package model;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import repository.*;

/**
 * Created by mihkel on 6.04.2018.
 */
@Configuration
public class AppConfig {

    @Value( "${mongoDB.mongoHost}" )
    private String mongoHost;
    @Value( "${mongoDB.mongoPort}" )
    private Integer mongoPort;
    @Value( "${mongoDB.DBName}" )
    private String mongoDBName;

    public @Bean
    MongoDbFactory mongoDbSimpleFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(mongoHost, mongoPort), mongoDBName);
    }

    public @Bean
    MongoOperations mongoOperations() throws Exception {
        return new MongoTemplate(mongoDbSimpleFactory());
    }

    public @Bean
    EventDao mongdoDBEventDao() throws Exception {
        return new MongoDBEventDao();
    }

    public @Bean
    MemberDao mongdoDBMemberDao() throws Exception {
        return new MongoDBMemberDao();
    }
    public @Bean
    TransactionDao mongdoDBTransactionDao() throws Exception {
        return new MongoDBTransactionDao();
    }
}