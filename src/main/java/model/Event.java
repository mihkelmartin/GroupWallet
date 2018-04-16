package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private ArrayList<Member> members = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Event (String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.PIN = (short) Math.floor(Math.random()*(10000));
    }

    public void update(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Short getPIN() {
        return PIN;
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
