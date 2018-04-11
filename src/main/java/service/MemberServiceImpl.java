package service;

import model.Event;
import model.Member;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mihkel on 11.04.2018.
 */
public class MemberServiceImpl implements MemberService {

    @Autowired
    TransactionService transactionService;

    @Override
    public Member createNew(Event event, String name, String nickName, String eMail, String bankAccount) {
        Member retVal = new Member(name, nickName, eMail, bankAccount, event.getNextOrderNr(event.getMembers()), event);
        retVal.addToSet(event.getMembers());
        transactionService.addMemberToTransactions(retVal);
        return retVal;
    }

    @Override
    public Member create(Event event, String id, String name, String nickName, String eMail, String bankAccount, int order) {
        return null;
    }

    @Override
    public Member update(Member member, String name, String nickName, String eMail, String bankAccount, int order) {
        member.update(name, nickName, eMail, bankAccount, order);
        return member;
    }

    @Override
    public Member remove(Member member) {
        transactionService.removeMemberToTransactions(member);
        member.removeFromSet(member.getEvent().getMembers());
        return member;
    }
}
