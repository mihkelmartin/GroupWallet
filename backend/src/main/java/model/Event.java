package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed
    private @NonNull String id;
    private @NonNull String name = "";
    @Indexed
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
        setId(UUID.randomUUID().toString());
        generatePIN();
        setEventCreatedTS(new Date());
        setEventAccessedTS(getEventCreatedTS());
        setSecurityTokenGenTS(getEventCreatedTS());
    }

    public void update(Event updatedEvent){
        setName(updatedEvent.getName());
        setOwnerEmail(updatedEvent.getOwnerEmail());
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
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

    private void setPIN(Long PIN) {
        this.PIN = PIN;
    }

    public Date getEventCreatedTS() {
        return eventCreatedTS;
    }

    private void setEventCreatedTS(Date eventCreatedTS) {
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
        setPIN((long) Math.floor(Math.random()*(100000)));
    }

    public void generatePUK(){
        setPIN((long) Math.floor(Math.random()*(1000000)));
    }

    public String generateToken(){
        String retVal = this.securityToken = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        setSecurityTokenGenTS(new Date());
        return retVal;
    }

}
