package service;

import model.Event;
import model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import repository.TransactionDao;

public class TransactionFactory  {

    @Autowired
    private TransactionDao transactionDao;

    public Transaction add(Event event, String name, boolean bmanualCalculation) {
        Transaction retVal = new Transaction(name, bmanualCalculation,
                event.getNextOrderNr(event.getTransactions()), event);
        event.getTransactions().add(retVal);
        return retVal;
    }

    public Transaction save(Transaction transaction, String name, boolean bmanualCalculation) {
        transaction.update(name, bmanualCalculation, transaction.getOrder());
        return transaction;
    }

    public Transaction remove(Transaction transaction) {
        transaction.getEvent().getTransactions().remove(transaction);
        return transaction;
    }

    public void loadTransactions(Event event){
        transactionDao.loadTransactions(event);
    };
}
