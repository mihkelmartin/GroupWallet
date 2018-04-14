package service;

import model.Event;
import model.Member;

/**
 * Created by mihkel on 11.04.2018.
 */
public interface MemberService {
    Member add(Event event, String name, String nickName, String eMail, String bankAccount);
    Member save(Member member, String name, String nickName, String eMail,
                String bankAccount);
    Member remove(Member member);
    void loadMembers(Event event);
}
