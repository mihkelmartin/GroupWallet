package repository;

import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/**
 * Created by mihkel on 6.04.2018.
 */

@Repository
public class MongoDBEventDao implements EventDao {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOps;

    @Override
    public void add(Event event) {
        save(event);
    }

    @Override
    public void save(Event event) {
        mongoOps.save(event);
    }

    @Override
    public void remove(Event event) {
        mongoOps.remove(event);
    }

    @Override
    public Event loadEvent(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoOps.findOne(query, Event.class);
    }

    @Override
    public List<Event> loadEventsByemail(String eMail) {
        Query query = new Query();
        query.addCriteria(Criteria.where("members.eMail").is(eMail));
        return mongoOps.find(query, Event.class);
    }

}
