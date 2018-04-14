package service;

import model.Event;
import model.Transaction;

public class TransactionFactory  {

    public Transaction add(Event event, String name, boolean bmanualCalculation) {
        Transaction retVal = new Transaction(name, bmanualCalculation,
                event.getNextOrderNr(event.getTransactions()), event);
        return retVal;
    }

    public Transaction save(Transaction transaction, String name, boolean bmanualCalculation, int order) {
        transaction.update(name, bmanualCalculation, order);
        return transaction;
    }

    public Transaction remove(Transaction transaction) {
        transaction.getEvent().getTransactions().remove(transaction);
        return transaction;
    }
}
