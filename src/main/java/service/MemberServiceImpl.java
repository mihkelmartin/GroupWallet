package service;

import model.Event;
import model.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by mihkel on 11.04.2018.
 */
public class MemberServiceImpl implements MemberService {

    @Autowired
    TransactionService transactionService;

    @Autowired
    MemberFactory memberFactory;

    @Override
    public Member add(Event event, String name, String nickName, String eMail, String bankAccount) {
        Member retVal = memberFactory.add(event, name, nickName, eMail, bankAccount);
        transactionService.addMemberToTransactions(retVal);
        return retVal;
    }

    @Override
    public Member save(Member member, String name, String nickName, String eMail, String bankAccount) {
        memberFactory.save(member, name, nickName, eMail, bankAccount, member.getOrder());
        return member;
    }

    @Override
    public Member remove(Member member) {
        transactionService.removeMemberFromTransactions(member);
        memberFactory.remove(member);
        recalculateOrderNumbers(member);
        return member;
    }

    @Override
    public void loadMembers(Event event) {
        if(event != null)
            memberFactory.loadMembers(event);
    }

    private void recalculateOrderNumbers(Member removed){
        for(Member member : removed.getEvent().getMembers()){
            if(member.getOrder() > removed.getOrder())
                member.setOrder(member.getOrder() - 1);
        }
    }
}
