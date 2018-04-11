package repository;

import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


/**
 * Created by mihkel on 6.04.2018.
 */

@Repository
public class MongoDBEventDao implements EventDao {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOps;

    @Override
    public void save(Event event) {
        mongoOps.save(event);
    }

    @Override
    public Event findEvent(String attribute, String value) {
        Query query = new Query();
        query.addCriteria(Criteria.where(attribute).is(value));
        return mongoOps.findOne(query, Event.class);
    }
}
