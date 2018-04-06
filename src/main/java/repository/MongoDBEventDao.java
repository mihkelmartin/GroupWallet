package repository;

import com.mongodb.client.MongoDatabase;
import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDbFactory;


/**
 * Created by mihkel on 6.04.2018.
 */

public class MongoDBEventDao implements EventDao {

    private MongoDatabase db;

    @Autowired
    public MongoDBEventDao (MongoDbFactory mongoDbFactory){
      this.db = mongoDbFactory.getDb();
    }

    @Override
    public void add(Event event) {

    }
}
