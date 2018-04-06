package model;


import org.springframework.lang.NonNull;


/**
 * Created by mihkel on 5.04.2018.
 */
public class TransactionItem {


    private final @NonNull String transactionid;
    private final Member member;
    private double debet;
    private double credit;
    private boolean bcreditAutoCalculated;

    public TransactionItem(String transactionid, Member member) {
        this.transactionid = transactionid;
        this.member = member;
        this.debet = 0.0;
        this.credit = 0.0;
        this.bcreditAutoCalculated = true;
    }

    public Member getMember() {
        return member;
    }
}
