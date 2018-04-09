package repository;

import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;


/**
 * Created by mihkel on 6.04.2018.
 */

public class MongoDBTransactionDao implements TransactionDao {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOps;

    @Override
    public void save(Transaction transaction) {
        mongoOps.save(transaction.getEvent());
    }
}
