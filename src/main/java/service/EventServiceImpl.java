package service;

import model.Event;
import model.Member;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import repository.EventDao;

/**
 * Created by mihkel on 11.04.2018.
 */
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Override
    public Event createNew(String name) {
        return new Event(name);
    }

    @Override
    public void update(Event event, String name) {
        event.update(name);
    }

    @Override
    public Event findEvent(String attribute, String value) {
        return eventDao.findEvent(attribute, value);
    }

    @Override
    public Member findMember(Event event, String id) {
        Member retVal = null;
        for( Member member : event.getMembers()){
            if(member.getId() == id)
                retVal = member;
        }
        return retVal;
    }

    @Override
    public Transaction findTransaction(Event event, String id) {
        Transaction retVal = null;
        for( Transaction transaction: event.getTransactions()){
            if(transaction.getId() == id)
                retVal = transaction;
        }
        return retVal;
    }
}

