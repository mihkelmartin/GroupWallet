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
    private @NonNull String name = "";
    private @NonNull String ownerEmail = "";
    @JsonIgnore
    private final @NonNull Long PIN;
    @JsonIgnore
    private @NonNull Short failedLogins = 0;
    @JsonIgnore
    private @NonNull String securityToken = "";
    @JsonIgnore
    private @NonNull String securityTokenGenTS = "";
    @JsonIgnore
    private ArrayList<Member> members = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Event (){
        this.id = UUID.randomUUID().toString();
        this.PIN = (long) Math.floor(Math.random()*(100000));
    }

    public void update(Event updatedEvent){
        setName(updatedEvent.getName());
        setOwnerEmail(updatedEvent.getOwnerEmail());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Short getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(Short failedLogins) {
        this.failedLogins = failedLogins;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getSecurityTokenGenTS() {
        return securityTokenGenTS;
    }

    public void setSecurityTokenGenTS(String securityTokenGenTS) {
        this.securityTokenGenTS = securityTokenGenTS;
    }

    @JsonIgnore
    public Long getPIN() {
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
