package repository;

import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;


/**
 * Created by mihkel on 6.04.2018.
 */

public class MongoDBEventDao implements EventDao {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOps;

    @Override
    public void add(Event event) {
        mongoOps.save(event);
    }
}
