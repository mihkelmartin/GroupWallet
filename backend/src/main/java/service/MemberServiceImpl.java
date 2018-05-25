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
    public Member add(Event event, Member newMember) {
        // Set technical attributes before copy
        newMember.setEvent(event);
        newMember.setOrder(event.getNextOrderNr(event.getMembers()));

        Member retVal = new Member();
        retVal.update(newMember);

        event.getMembers().add(retVal);
        memberDao.add(retVal);
        transactionService.addMemberToTransactions(retVal);
        return retVal;
    }

    @Override
    public Member save(Member member, Member updatedMember) {
        // Set technical attributes before copy
        updatedMember.setEvent(member.getEvent());

        member.update(updatedMember);
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
