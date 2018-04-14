package service;

import model.Event;
import model.Member;
import model.Transaction;
import model.TransactionItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mihkel on 11.04.2018.
 */
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionFactory transactionFactory;
    @Autowired
    private TransactionItemFactory transactionItemFactory;


    public Transaction add(Event event, String name, boolean bmanualCalculation) {
        Transaction retVal = transactionFactory.add(event, name, bmanualCalculation);
        populateTransactionItems(retVal, event.getMembers());
        return retVal;
    }

    public Transaction save(Transaction transaction, String name, boolean bmanualCalculation) {
        transactionFactory.save(transaction, name, bmanualCalculation);
        return transaction;
    }

    public Transaction remove(Transaction transaction) {
        removeTransactionItemsFromTransaction(transaction);
        transactionFactory.remove(transaction);
        recalculateOrderNumbers(transaction);
        return transaction;
    }

    private void recalculateOrderNumbers(Transaction removed){
        for(Transaction transaction: removed.getEvent().getTransactions()){
            if(transaction.getOrder() > removed.getOrder())
                transaction.setOrder(transaction.getOrder() - 1);
        }
    }

    public Member addMemberToTransactions(Member member) {
        for(Transaction transaction : member.getEvent().getTransactions()){
            transactionItemFactory.add(transaction, member);
        }
        return member;
    }

    public Member removeMemberFromTransactions(Member member) {
        for(Transaction transaction : member.getEvent().getTransactions()){
            removeTransactionItemsWithMember(transaction, member);
        }
        return member;
    }

    public TransactionItem addDebitForMember(Transaction transaction, Member member, double debit){
        TransactionItem transactionItem = null;
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId().equals(member.getId())) {
                transactionItemFactory.save(item, debit, item.getCredit(), item.isBcreditAutoCalculated());
                transactionItem = item;
                break;
            }
        }
        return transactionItem;
    }

    public TransactionItem addCreditForMember(Transaction transaction, Member member, double credit){
        TransactionItem transactionItem = null;
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId().equals(member.getId())) {
                transactionItemFactory.save(item, item.getDebit(), credit, false);
                transactionItem = item;
                break;
            }
        }
        return transactionItem;
    }

    public TransactionItem setAutoCalculationForMember(Transaction transaction, Member member, boolean bAutoCalculation){
        TransactionItem transactionItem = null;
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId().equals(member.getId())) {
                transactionItemFactory.save(item, item.getDebit(), item.getCredit(), bAutoCalculation);
                transactionItem = item;
                break;
            }
        }
        return transactionItem;
    }

    private void populateTransactionItems(Transaction transaction, ArrayList<Member> members) {
        for(Member member : members){
            transactionItemFactory.add(transaction, member);
        }
    }

    private void removeTransactionItemsWithMember(Transaction transaction, Member member){
        Iterator<TransactionItem> iter = transaction.getItems().iterator();
        while (iter.hasNext()) {
            TransactionItem transactionItem = iter.next();
            if (transactionItem.getMemberId().equals(member.getId())) {
                transactionItemFactory.remove(transaction, transactionItem);
                iter.remove();
            }
        }
    }

    private void removeTransactionItemsFromTransaction(Transaction transaction){
        Iterator<TransactionItem> iter = transaction.getItems().iterator();
        while (iter.hasNext()) {
            TransactionItem transactionItem = iter.next();
            transactionItemFactory.remove(transaction, transactionItem);
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
                    transactionItemFactory.save(item, item.getDebit(), dAutoCalculatedCredit, item.isBcreditAutoCalculated());
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

    public void loadTransactions(Event event) {
        if(event != null) {
            transactionFactory.loadTransactions(event);
            for(Transaction transaction : event.getTransactions()){
                transactionItemFactory.loadTransactionItems(transaction);
            }
        }
    }
}
