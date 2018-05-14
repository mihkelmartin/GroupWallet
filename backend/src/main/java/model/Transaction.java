package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isBmanualCalculation() {
        return bmanualCalculation;
    }

    public ArrayList<TransactionItem> getItems() {
        return items;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public int compareTo(Transaction o) {
        return (this.getOrder() < o.getOrder() ? -1 :
                (this.getOrder() == o.getOrder() ? 0 : 1));
    }

}
