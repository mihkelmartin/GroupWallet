package model;

import aspects.DAOAspect;
import aspects.MoneyCalculationAspect;
import com.mongodb.MongoClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import repository.*;
import service.*;


/**
 * Created by mihkel on 6.04.2018.
 */
@Configuration
@EnableAspectJAutoProxy
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

    public @Bean
    EventService eventService() throws Exception {
        return new EventServiceImpl();
    }

    public @Bean
    MemberService memberService() throws Exception {
        return new MemberServiceImpl();
    }

    public @Bean
    TransactionService transactionService() throws Exception {
        return new TransactionServiceImpl();
    }

    public @Bean
    MoneyCalculationAspect moneyCalculationAspect() {
        return new MoneyCalculationAspect();
    }

    public @Bean
    DAOAspect daoAspect() {
        return new DAOAspect();
    }

}