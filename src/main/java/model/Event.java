package model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.core.Ordered;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by mihkel on 5.04.2018.
 */

@Document
public class Event {

    @Id
    private final @NonNull String id;
    private @NonNull String name;
    private final @NonNull Short PIN;
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    @Autowired
    private Supplier<Member> memberSupplier;
    @Autowired
    private Supplier<Transaction> transactionSupplier;

    public Event (String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.PIN = (short) Math.floor(Math.random()*(10000));
    }

    public void update(String name){
        this.name = name;
    }

    public Member addMember(String name, String nickName, String eMail, String bankAccount){
        Member retVal = memberSupplier.get();
        retVal.update(name, nickName, eMail, bankAccount, getNextOrderNr(members), this);
        retVal.addToSet(members);
        addMemberToTransactions(retVal);
        return retVal;
    }

    protected void addMemberToTransactions(Member member){
        for(Transaction transaction : transactions){
            transaction.addTransactionItem(member);
        }
    }

    public Member removeMember(Member member){
        removeMemberFromTransactions(member);
        member.removeFromSet(members);
        return member;
    }

    protected void removeMemberFromTransactions(Member member){
        for(Transaction transaction : transactions){
            transaction.removeTransactionItemsWithMember(member);
        }
    }

    public Transaction addTransaction(String name) {
        Transaction retVal = transactionSupplier.get();
        retVal.update(name, getNextOrderNr(transactions), this);
        retVal.addToSet(transactions);
        retVal.populateTransactionItems(members);
        return retVal;
    }

    public Transaction removeTransaction(Transaction transaction) {
        transaction.removeFromSet(transactions);
        return transaction;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
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
