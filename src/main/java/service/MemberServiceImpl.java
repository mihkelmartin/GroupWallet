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
    private MemberDao memberDao;

    @Autowired
    TransactionService transactionService;

    @Override
    public Member add(Event event, String name, String nickName, String eMail, String bankAccount) {
        Member retVal = new Member(name, nickName, eMail, bankAccount, event.getNextOrderNr(event.getMembers()), event);
        event.getMembers().add(retVal);
        transactionService.addMemberToTransactions(retVal);
        return retVal;
    }

    @Override
    public Member save(Member member, String name, String nickName, String eMail, String bankAccount, int order) {
        member.update(name, nickName, eMail, bankAccount, order);
        return member;
    }

    @Override
    public Member remove(Member member) {
        transactionService.removeMemberFromTransactions(member);
        member.getEvent().getMembers().remove(member);
        return member;
    }

    @Override
    public void loadMembers(Event event) {
        if(event != null)
            memberDao.loadMembers(event);
    }
}
