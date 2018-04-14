package service;

import model.Event;

public class EventFactory  {
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
}
