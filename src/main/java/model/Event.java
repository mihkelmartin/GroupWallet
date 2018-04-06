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

public class Event {

    @Id
    private final @NonNull String id;
    private @NonNull String name;
    private final @NonNull Short PIN;
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Event (String name){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.PIN = (short) Math.floor(Math.random()*(10000));
    }

    public Member addMember(String name, String nickName, String eMail, String bankAccount){
        Member retVal = new Member(name, nickName, eMail, bankAccount, getNextOrderNr(members));
        members.add(retVal);
        addMemberToTransactions(retVal);
        return retVal;
    }

    public void addMemberToTransactions(Member member){
        for(Transaction transaction : transactions){
            transaction.addTransactionItem(member);
        }
    }

    public void removeMember(Member member){
        removeMemberFromTransactions(member);
        members.remove(member);
    }

    public void removeMemberFromTransactions(Member member){
        for(Transaction transaction : transactions){
            transaction.removeTransactionItemsWithMember(member);
        }
    }

    public Transaction addTransaction(String name) {
        Transaction retVal = new Transaction(name, getNextOrderNr(transactions));
        transactions.add(retVal);
        retVal.populateTransactionItems(members);
        return retVal;
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
