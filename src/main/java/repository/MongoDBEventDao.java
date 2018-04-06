package repository;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Event;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/**
 * Created by mihkel on 6.04.2018.
 */

public class MongoDBEventDao implements EventDao {

    @Value( "${mongoDB.CollectionName}" )
    private String collectionName;

    @Autowired
    private MongoDatabase db;

    @Override
    public void add(Event event) {
        Gson gson=new Gson();
        String json = gson.toJson(event);
        BasicDBObject basicDBObject = new BasicDBObject(event.getId(), json );
        MongoCollection<Document> coll = db.getCollection(collectionName);
        coll.insertOne(new Document(basicDBObject));
    }
}
