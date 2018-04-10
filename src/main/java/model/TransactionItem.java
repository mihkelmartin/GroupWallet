package model;


import org.springframework.lang.NonNull;

import java.util.ArrayList;


/**
 * Created by mihkel on 5.04.2018.
 */
public class TransactionItem {

    private @NonNull String transactionId;
    private @NonNull String memberId;
    private double debit;
    private double credit;
    private boolean bcreditAutoCalculated;

    public TransactionItem(){

    }

    public void update(String transactionId, String memberId) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.debit = 0.0;
        this.credit = 0.0;
        this.bcreditAutoCalculated = true;
    }

    public void addToSet(ArrayList<TransactionItem> transactionItems){
        transactionItems.add(this);
    }
    public String getMemberId() {
        return memberId;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public boolean isBcreditAutoCalculated() {
        return bcreditAutoCalculated;
    }

    public void setBcreditAutoCalculated(boolean bcreditAutoCalculated) {
        this.bcreditAutoCalculated = bcreditAutoCalculated;
    }
}
