package repository;


import model.Event;
import model.Transaction;
import model.TransactionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import service.EventService;
import service.TransactionService;


/**
 * Created by mihkel on 6.04.2018.
 */

@Repository
public class MongoDBTransactionItemDao implements TransactionItemDao {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOps;

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public void add(TransactionItem transactionItem) {

    }

    @Override
    public void save(TransactionItem transactionItem) {
        transactionDao.save(transactionItem.getTransaction());
    }

    @Override
    public void remove(TransactionItem transactionItem) {

    }

    @Override
    public void loadTransactionItems(Transaction transaction) {

    }

}
