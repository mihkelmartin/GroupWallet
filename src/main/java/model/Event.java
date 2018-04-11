package model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by mihkel on 5.04.2018.
 */

@Document
public class Event {

    @Id
    private @NonNull String id;
    private @NonNull String name;
    private final @NonNull Short PIN;
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Event (String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.PIN = (short) Math.floor(Math.random()*(10000));
    }

    public void update(String name){
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getNextOrderNr(ArrayList arrayList){
        int newOrderNr = 1;
        if(!arrayList.isEmpty()){
            Collections.reverse(arrayList);
            newOrderNr = ((Ordered)arrayList.get(0)).getOrder() + 1;
        }
        return newOrderNr;
    }

}
