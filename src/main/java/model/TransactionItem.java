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

    public TransactionItem(String transactionId, String memberId, double debit, double credit, boolean bcreditAutoCalculated) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.debit = debit;
        this.credit = credit;
        this.bcreditAutoCalculated = bcreditAutoCalculated;
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
