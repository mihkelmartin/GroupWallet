package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by mihkel on 5.04.2018.
 */
public class Transaction implements Comparable<Transaction>, Ordered {

    @Id
    private final @NonNull String id;
    private @NonNull String name;
    private boolean bmanualCalculation;
    private @NonNull int order;
    private ArrayList<TransactionItem> items = new ArrayList<>();
    @Transient
    private @NonNull Event event;

    @Autowired
    private Supplier<TransactionItem> transactionItemSupplier;

    public Transaction(){
        this.id = UUID.randomUUID().toString();
    }

    public void update(String name, int order, Event event) {
        this.name = name;
        bmanualCalculation = false;
        this.order = order;
        this.event = event;
    }

    public void addToSet(ArrayList<Transaction> transactions){
        transactions.add(this);
    }

    protected TransactionItem addTransactionItem(Member member) {
        TransactionItem retVal = transactionItemSupplier.get();
        retVal.update(this.id, member.getId());
        retVal.addToSet(items);
        return retVal;
    }

    protected void removeTransactionItemsWithMember(Member member){
        Iterator<TransactionItem> iter = items.iterator();
        while (iter.hasNext()) {
            TransactionItem transactionItem = iter.next();
            if (transactionItem.getMemberId() == member.getId())
                iter.remove();
        }
    }

    protected void populateTransactionItems(ArrayList<Member> members) {
        for(Member member : members){
            TransactionItem transactionItem = transactionItemSupplier.get();
            transactionItem.update(this.id, member.getId());
            items.add(transactionItem);
        }
    }

    public void addDebitForMember(String memberId, double debit){
        for(TransactionItem item : items){
            if(item.getMemberId() == memberId)
                item.setDebit(debit);
        }
    }

    public void addCreditForMember(String memberId, double credit){
        for(TransactionItem item : items){
            if(item.getMemberId() == memberId) {
                item.setCredit(credit);
                item.setBcreditAutoCalculated(false);
            }
        }
    }

    public void setAutoCalculationOnForMember(String memberId){
        for(TransactionItem item : items){
            if(item.getMemberId() == memberId)
                item.setBcreditAutoCalculated(true);
        }
    }

    public void calculateCredits(){

        double dAutoCalculatedCredit = 0.0;
        int lAutoCalculatedCreditCount = getAutoCalculatedCreditCount();
        if(lAutoCalculatedCreditCount != 0) {
            dAutoCalculatedCredit = (getDebit() - getManualCredit()) / getAutoCalculatedCreditCount();
            for(TransactionItem item : items){
                if(item.isBcreditAutoCalculated())
                    item.setCredit(dAutoCalculatedCredit);
            }
        }
    }

    private double getDebit(){
        double retVal = 0.0;
        for(TransactionItem item : items){
            retVal += item.getDebit();
        }
        return retVal;
    }

    private double getManualCredit(){
        double retVal = 0.0;
        for(TransactionItem item : items){
            if(!item.isBcreditAutoCalculated())
                retVal += item.getCredit();
        }
        return retVal;
    }

    private int getAutoCalculatedCreditCount(){
        int retVal = 0;
        for(TransactionItem item : items){
            if(item.isBcreditAutoCalculated())
                retVal ++;
        }
        return retVal;
    }


    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public int compareTo(Transaction o) {
        return (this.getOrder() < o.getOrder() ? -1 :
                (this.getOrder() == o.getOrder() ? 0 : 1));
    }

}
