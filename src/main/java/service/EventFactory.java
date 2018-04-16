package service;

import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import repository.EventDao;

import java.util.List;

public class EventFactory  {

    @Autowired
    private EventDao eventDao;

    public Event add(String name) {
        return new Event(name);
    }

    public Event save(Event event, String name) {
        event.update(name);
        return event;
    }

    public Event remove(Event event) {
        return event;
    }

    public Event loadEvent(String id) {
        Event event = eventDao.loadEvent(id);
        return event;
    }

    public List<Event> loadEventsByemail(String eMail) {
        return eventDao.loadEventsByemail(eMail);
    }
}
