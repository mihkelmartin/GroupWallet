package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

import java.util.*;

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
    private @NonNull Long PIN;
    @JsonIgnore
    private @NonNull Date eventCreatedTS;
    @JsonIgnore
    private @NonNull Date eventAccessedTS;
    @JsonIgnore
    private @NonNull Short failedLogins = 0;
    @JsonIgnore
    private @NonNull String securityToken = "";
    @JsonIgnore
    private @NonNull Date securityTokenGenTS;
    @JsonIgnore
    private ArrayList<Member> members = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Event (){
        this.id = UUID.randomUUID().toString();
        generatePIN();
        this.eventCreatedTS = new Date();
        this.eventAccessedTS = this.eventCreatedTS;
        this.securityTokenGenTS = this.eventCreatedTS;
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

    public Long getPIN() {
        return PIN;
    }

    public Date getEventCreatedTS() {
        return eventCreatedTS;
    }

    public void setEventCreatedTS(Date eventCreatedTS) {
        this.eventCreatedTS = eventCreatedTS;
    }

    public Date getEventAccessedTS() {
        return eventAccessedTS;
    }

    public void setEventAccessedTS(Date eventAccessedTS) {
        this.eventAccessedTS = eventAccessedTS;
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

    public Date getSecurityTokenGenTS() {
        return securityTokenGenTS;
    }

    public void setSecurityTokenGenTS(Date securityTokenGenTS) {
        this.securityTokenGenTS = securityTokenGenTS;
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

    public void generatePIN(){
        this.PIN = (long) Math.floor(Math.random()*(100000));
    }

    public void generatePUK(){
        this.PIN = (long) Math.floor(Math.random()*(1000000));
    }

    public void generateToken(){
        this.securityToken = UUID.randomUUID().toString() + UUID.randomUUID().toString();
    }

}
