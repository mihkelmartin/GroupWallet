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
        event.getTransactions().add(retVal);
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
        transaction.getEvent().getTransactions().remove(transaction);
        return transaction;
    }

    @Override
    public Member addMemberToTransactions(Member member) {
        for(Transaction transaction : member.getEvent().getTransactions()){
            addTransactionItem(transaction, member);
        }
        return member;
    }

    @Override
    public Member removeMemberToTransactions(Member member) {
        for(Transaction transaction : member.getEvent().getTransactions()){
            removeTransactionItemsWithMember(transaction, member);
        }
        return member;
    }

    public Transaction addDebitForMember(Transaction transaction, Member member, double debit){
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId().equals(member.getId()))
                item.setDebit(debit);
        }
        return transaction;
    }


    public Transaction addCreditForMember(Transaction transaction, Member member, double credit){
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId().equals(member.getId())) {
                item.setCredit(credit);
                item.setBcreditAutoCalculated(false);
            }
        }
        return transaction;
    }

    public Transaction setAutoCalculationForMember(Transaction transaction, Member member, boolean bAutoCalculation){
        for(TransactionItem item : transaction.getItems()){
            if(item.getMemberId().equals(member.getId()))
                item.setBcreditAutoCalculated(bAutoCalculation);
        }
        return transaction;
    }

    private TransactionItem addTransactionItem(Transaction transaction, Member member) {
        TransactionItem retVal = new TransactionItem(transaction.getId(), member.getId(),
                0.0, 0.0, true);
        transaction.getItems().add(retVal);
        return retVal;
    }

    private void populateTransactionItems(Transaction transaction, ArrayList<Member> members) {
        for(Member member : members){
            TransactionItem transactionItem = new TransactionItem(transaction.getId(), member.getId(),
                    0.0, 0.0, true);
            transaction.getItems().add(transactionItem);
        }
    }

    private void removeTransactionItemsWithMember(Transaction transaction, Member member){
        Iterator<TransactionItem> iter = transaction.getItems().iterator();
        while (iter.hasNext()) {
            TransactionItem transactionItem = iter.next();
            if (transactionItem.getMemberId().equals(member.getId()))
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
