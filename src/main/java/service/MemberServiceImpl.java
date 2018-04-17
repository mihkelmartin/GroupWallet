package service;

import model.Event;
import model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import repository.MemberDao;


/**
 * Created by mihkel on 11.04.2018.
 */
public class MemberServiceImpl implements MemberService {

    @Autowired
    TransactionService transactionService;

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member add(Event event, String name, String nickName, String eMail, String bankAccount) {
        Member retVal = new Member(name, nickName, eMail, bankAccount, event.getNextOrderNr(event.getMembers()), event);
        event.getMembers().add(retVal);
        memberDao.add(retVal);
        transactionService.addMemberToTransactions(retVal);
        return retVal;
    }

    @Override
    public Member save(Member member, String name, String nickName, String eMail, String bankAccount) {
        member.update(name, nickName, eMail, bankAccount, member.getOrder());
        memberDao.save(member);
        return member;
    }

    @Override
    public Member remove(Member member) {
        transactionService.removeMemberFromTransactions(member);
        member.getEvent().getMembers().remove(member);
        memberDao.remove(member);
        recalculateOrderNumbers(member);
        return member;
    }

    @Override
    public void loadMembers(Event event) {
        if(event != null)
            memberDao.loadMembers(event);
    }

    private void recalculateOrderNumbers(Member removed){
        for(Member member : removed.getEvent().getMembers()){
            if(member.getOrder() > removed.getOrder()){
                member.setOrder(member.getOrder() - 1);
                memberDao.save(member);
            }
        }
    }
}
