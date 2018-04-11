package service;

import model.Event;
import model.Member;
import model.Transaction;

/**
 * Created by mihkel on 11.04.2018.
 */
public interface EventService {
    Event createNew(String name);
    void update(Event event, String name);
    Event findEvent(String attribute, String value);
    Member findMember(Event event, String id);
    Transaction findTransaction(Event event, String id);
}
