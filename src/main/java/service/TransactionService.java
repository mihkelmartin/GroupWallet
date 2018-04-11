package service;

import model.Event;
import model.Member;
import model.Transaction;

/**
 * Created by mihkel on 11.04.2018.
 */
public interface TransactionService {
    Transaction createNew(Event event, String name, boolean bmanualCalculation);
    Transaction create(Event event, String id, String name, boolean bmanualCalculation, int order);
    Transaction update(Transaction transaction, String name, boolean bmanualCalculation, int order);
    Transaction remove(Transaction transaction);
    void addMemberToTransactions(Member member);
    void removeMemberToTransactions(Member member);
    void addDebitForMember(Transaction transaction, Member member, double debit);
    void addCreditForMember(Transaction transaction, Member member, double credit);
    void setAutoCalculationForMember(Transaction transaction, Member member, boolean bAutoCalculation);

}
