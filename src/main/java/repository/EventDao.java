package repository;

import model.Event;

/**
 * Created by mihkel on 6.04.2018.
 */
public interface EventDao extends GeneralDao<Event> {
    Event loadEvent(String id);
}
