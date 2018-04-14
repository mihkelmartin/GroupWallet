package service;

import model.Member;
import model.Transaction;
import model.TransactionItem;
import org.springframework.beans.factory.annotation.Autowired;
import repository.TransactionItemDao;

public class TransactionItemFactory {

    @Autowired
    private TransactionItemDao transactionItemDao;

    public TransactionItem add(Transaction transaction, Member member) {
        TransactionItem retVal = new TransactionItem(transaction.getId(), member.getId(),
                0.0, 0.0, true, transaction);
        transaction.getItems().add(retVal);
        return retVal;
    }

    public TransactionItem save(TransactionItem transactionItem, double debit,
                                double credit, boolean bcreditAutoCalculated) {
        transactionItem.update(debit, credit, bcreditAutoCalculated);
        return transactionItem;
    }

    public TransactionItem remove(Transaction transaction, TransactionItem transactionItem) {
        // Do nothing, triggers DAO deleting via AOP
        return transactionItem;
    }

    public void loadTransactionItems(Transaction transaction){
        transactionItemDao.loadTransactionItems(transaction);
    };
}
