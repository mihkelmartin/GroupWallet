package model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import repository.EventDao;
import repository.MongoDBEventDao;

/**
 * Created by mihkel on 6.04.2018.
 */
@Configuration
public class AppConfig {

    @Value( "${mongoDB.DBName}" )
    private String mongoDBName;

    public @Bean
    MongoDbFactory mongoDbSimpleFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(), mongoDBName);
    }

    public @Bean
    MongoDatabase mongoDatabase() throws Exception {
        return mongoDbSimpleFactory().getDb();
    }

    public @Bean
    EventDao mongdoDBEventDao() throws Exception {
        return new MongoDBEventDao();
    }

}