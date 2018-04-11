package service;

import model.Event;
import model.Member;

/**
 * Created by mihkel on 11.04.2018.
 */
public interface MemberService {
    Member createNew(Event event, String name, String nickName, String eMail, String bankAccount);
    Member create(Event event, String id, String name,  String nickName, String eMail,
                  String bankAccount, int order);
    Member update(Member member, String name,  String nickName, String eMail,
                  String bankAccount, int order);
    Member removeMember(Member member);

}
