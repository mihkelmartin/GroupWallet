package model;


import org.springframework.lang.NonNull;


/**
 * Created by mihkel on 5.04.2018.
 */
public class TransactionItem {

    private final @NonNull String transactionId;
    private final @NonNull String memberId;
    private double debit;
    private double credit;
    private boolean bcreditAutoCalculated;

    public TransactionItem(String transactionId, String memberId) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.debit = 0.0;
        this.credit = 0.0;
        this.bcreditAutoCalculated = true;
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
