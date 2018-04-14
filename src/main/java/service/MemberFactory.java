package service;

import model.Event;
import model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import repository.MemberDao;
import repository.TransactionDao;

public class MemberFactory   {

    @Autowired
    private MemberDao memberDao;

    public Member add(Event event, String name, String nickName, String eMail, String bankAccount) {
        Member retVal = new Member(name, nickName, eMail, bankAccount, event.getNextOrderNr(event.getMembers()), event);
        event.getMembers().add(retVal);
        return retVal;
    }

    public Member save(Member member, String name, String nickName, String eMail, String bankAccount, int order) {
        member.update(name, nickName, eMail, bankAccount, order);
        return member;
    }

    public Member remove(Member member) {
        member.getEvent().getMembers().remove(member);
        return member;
    }

    public void loadMembers(Event event) {
        if(event != null)
            memberDao.loadMembers(event);
    }
}
