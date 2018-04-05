package model;


//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mihkel on 5.04.2018.
 */

public class Event {

    private final @NonNull String ID;
    private @NonNull String name;
    private final @NonNull Short PIN;
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Event (String name){
        this.ID = UUID.randomUUID().toString();
        this.name = name;
        this.PIN = (short) Math.floor(Math.random()*(10000));
    }

    public void addMember(String name){
        members.add(new Member(name, getNextOrderNr(members)));
    }

    public void addTransaction(String name) {
        transactions.add(new Transaction(name, getNextOrderNr(transactions)));
    }

    private int getNextOrderNr(ArrayList arrayList){
        int newOrderNr = 1;
        if(!arrayList.isEmpty()){
            Collections.reverse(arrayList);
            newOrderNr = ((Ordered)arrayList.get(0)).getOrder() + 1;
        }
        return newOrderNr;
    }
}
