package repository;

import model.Transaction;

/**
 * Created by mihkel on 6.04.2018.
 */
public interface TransactionDao {

    public void save(Transaction transaction);
    public void remove(Transaction member);

}
