package model;

import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public Transaction(String name, int order) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        bmanualCalculation = false;
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(Transaction o) {
        return (this.getOrder() < o.getOrder() ? -1 :
                (this.getOrder() == o.getOrder() ? 0 : 1));
    }


    public TransactionItem addTransactionItem(Member member) {
        TransactionItem retVal = new TransactionItem(id, member);
        items.add(retVal);
        return retVal;
    }

    public void removeTransactionItemsWithMember(Member member){
        for(TransactionItem item : items){
            if(item.getMember() == member)
                items.remove(member);
        }
    }

    public void populateTransactionItems(ArrayList<Member> members) {
        for(Member member : members){
            addTransactionItem(member);
        }
    }

}
