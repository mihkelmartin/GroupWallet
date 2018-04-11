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
    private @NonNull String id;
    private @NonNull String name;
    private boolean bmanualCalculation;
    private @NonNull int order;
    private ArrayList<TransactionItem> items = new ArrayList<>();
    @Transient
    private @NonNull Event event;

    public Transaction(){

    }

    public Transaction(String name, boolean bmanualCalculation, int order, Event event){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.bmanualCalculation = bmanualCalculation;
        this.order = order;
        this.event = event;
    }

    public void update(String name, boolean bmanualCalculation, int order) {
        this.name = name;
        this.bmanualCalculation = bmanualCalculation;
        this.order = order;
    }

    public void addToSet(ArrayList<Transaction> transactions){
        transactions.add(this);
    }

    public void removeFromSet(ArrayList<Transaction> transactions){
        transactions.remove(this);
    }

    public String getId() {
        return id;
    }

    public ArrayList<TransactionItem> getItems() {
        return items;
    }

    public Event getEvent() {
        return event;
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

}
