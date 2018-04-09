package model;

import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.UUID;

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


    public Transaction(String name, int order, Event event) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        bmanualCalculation = false;
        this.order = order;
        this.event = event;
    }


    protected TransactionItem addTransactionItem(Member member) {
        TransactionItem retVal = new TransactionItem(this.id, member.getId());
        items.add(retVal);
        calculateCredits();
        return retVal;
    }

    protected void removeTransactionItemsWithMember(Member member){
        for(TransactionItem item : items){
            if(item.getMemberId() == member.getId())
                items.remove(member);
        }
        calculateCredits();
    }

    protected void populateTransactionItems(ArrayList<Member> members) {
        for(Member member : members){
            TransactionItem transactionItem = new TransactionItem(this.id, member.getId());
            items.add(transactionItem);
        }
    }

    public void addDebitForMember(String memberId, double debit){
        for(TransactionItem item : items){
            if(item.getMemberId() == memberId)
                item.setDebit(debit);
        }
        calculateCredits();
    }

    public void addCreditForMember(String memberId, double credit){
        for(TransactionItem item : items){
            if(item.getMemberId() == memberId) {
                item.setCredit(credit);
                item.setBcreditAutoCalculated(false);
            }
        }
        calculateCredits();
    }

    public void setAutoCalculationOnForMember(String memberId){
        for(TransactionItem item : items){
            if(item.getMemberId() == memberId)
                item.setBcreditAutoCalculated(true);
        }
        calculateCredits();
    }

    private void calculateCredits(){

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
