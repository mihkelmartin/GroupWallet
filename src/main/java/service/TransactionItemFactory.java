package service;

import model.Member;
import model.Transaction;
import model.TransactionItem;

public class TransactionItemFactory {

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
}
