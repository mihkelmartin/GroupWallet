package service;

import model.Event;
import model.Member;
import model.Transaction;
import model.TransactionItem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mihkel on 11.04.2018.
 */
public class TransactionServiceImpl implements TransactionService{

    @Override
    public Transaction createNew(Event event, String name, boolean bmanualCalculation) {
        Transaction retVal = new Transaction(name, bmanualCalculation,
                event.getNextOrderNr(event.getTransactions()), event);
        retVal.addToSet(event.getTransactions());
        populateTransactionItems(retVal, event.getMembers());
        return retVal;
    }

    @Override
    public Transaction create(Event event, String id, String name, boolean bmanualCalculation, int order) {
        return null;
    }

    @Override
    public Transaction update(Transaction transaction, String name, boolean bmanualCalculation, int order) {
        transaction.update(name, bmanualCalculation, order);
        return transaction;
    }

    @Override
    public Transaction remove(Transaction transaction) {
        transaction.removeFromSet(transaction.getEvent().getTransactions());
        return transaction;
    }

    @Override
    public void addMemberToTransactions(Member member) {
        for(Transaction transaction : member.getEvent().getTransactions()){
            addTransactionItem(transaction, member);
        }
    }

    @Override
    public void removeMemberToTransactions(Member member) {
        for(Transaction transaction : member.getEvent().getTransactions()){
            removeTransactionItemsWithMember(transaction, member);
        }
    }

    public void addDebitForMember(Transaction transaction, Member member, double debit){
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId() == member.getId())
                item.setDebit(debit);
        }
    }


    public void addCreditForMember(Transaction transaction, Member member, double credit){
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId() == member.getId()) {
                item.setCredit(credit);
                item.setBcreditAutoCalculated(false);
            }
        }
    }

    public void setAutoCalculationForMember(Transaction transaction, Member member, boolean bAutoCalculation){
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId() == member.getId())
                item.setBcreditAutoCalculated(bAutoCalculation);
        }
    }

    private TransactionItem addTransactionItem(Transaction transaction, Member member) {
        TransactionItem retVal = new TransactionItem();
        retVal.update(transaction.getId(), member.getId());
        retVal.addToSet(transaction.getItems());
        return retVal;
    }

    private void populateTransactionItems(Transaction transaction, ArrayList<Member> members) {
        for(Member member : members){
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.update(transaction.getId(), member.getId());
            transactionItem.addToSet(transaction.getItems());
        }
    }

    private void removeTransactionItemsWithMember(Transaction transaction, Member member){
        Iterator<TransactionItem> iter = transaction.getItems().iterator();
        while (iter.hasNext()) {
            TransactionItem transactionItem = iter.next();
            if (transactionItem.getMemberId() == member.getId())
                iter.remove();
        }
    }

    public void calculateCredits(Transaction transaction){

        double dAutoCalculatedCredit = 0.0;
        int lAutoCalculatedCreditCount = getAutoCalculatedCreditCount(transaction);
        if(lAutoCalculatedCreditCount != 0) {
            dAutoCalculatedCredit = (getDebit(transaction) - getManualCredit(transaction)) /
                    getAutoCalculatedCreditCount(transaction);
            for(TransactionItem item : transaction.getItems()){
                if(item.isBcreditAutoCalculated())
                    item.setCredit(dAutoCalculatedCredit);
            }
        }
    }

    private double getDebit(Transaction transaction){
        double retVal = 0.0;
        for(TransactionItem item : transaction.getItems()){
            retVal += item.getDebit();
        }
        return retVal;
    }

    private double getManualCredit(Transaction transaction){
        double retVal = 0.0;
        for(TransactionItem item : transaction.getItems()){
            if(!item.isBcreditAutoCalculated())
                retVal += item.getCredit();
        }
        return retVal;
    }

    private int getAutoCalculatedCreditCount(Transaction transaction){
        int retVal = 0;
        for(TransactionItem item : transaction.getItems()){
            if(item.isBcreditAutoCalculated())
                retVal ++;
        }
        return retVal;
    }

}
