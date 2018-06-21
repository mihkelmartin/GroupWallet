package service;

import model.Event;
import model.Member;
import model.Payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by mihkel on 21.06.2018.
 */
public class PaymentServiceImpl implements PaymentService{

    static final Comparator<Member> AMOUNT_ORDER =
            new Comparator<Member>() {
                public int compare(Member m1, Member m2) {
                    return (int)(m1.getBalance() - m2.getBalance());
                }
            };

    @Override
    public List<Payment> calculatePayments(Event event) {

        List<Payment> retVal = new ArrayList<>();
        List<Member> debitors = new ArrayList<>();
        List<Member> creditors = new ArrayList<>();
        fillArrays(event, debitors, creditors);
        createPaymentsExactMatch(retVal, debitors, creditors);
        createPaymentsPairMatch(retVal, debitors, creditors);
        createPaymentsRandom(retVal, debitors, creditors);
        return retVal;
    }

    private void fillArrays(Event event, List<Member> debitors, List<Member> creditors){
        event.getMembers().forEach(member ->
         {if(member.getBalance() > 0)
             creditors.add(member);
         else if (member.getBalance() < 0)
             debitors.add(member);
         });
        Collections.sort(debitors, AMOUNT_ORDER);
        Collections.sort(creditors, AMOUNT_ORDER);
    }

    private void createPaymentsExactMatch(List<Payment> payments,
                                          List<Member> debitors,
                                          List<Member> creditors ){
        for(Member memberCreditor : creditors) {
            for (Member memberDebitor : debitors) {
                if(memberCreditor.getBalance() == abs(memberDebitor.getBalance())){
                    Payment payment = new Payment();
                    payment.setAmount(memberCreditor.getBalance());
                    payment.setBankAccount(memberCreditor.getBankAccount());
                    payment.setPayor(memberDebitor.getName());
                    payment.setReceiver(memberCreditor.getName());
                    payment.setReceivereMail(memberCreditor.geteMail());
                    payments.add(payment);
                }
            }
        }
    }

    private void createPaymentsPairMatch(List<Payment> payments,
                                          List<Member> debitors,
                                          List<Member> creditors ){

    }
    private void createPaymentsRandom(List<Payment> payments,
                                         List<Member> debitors,
                                         List<Member> creditors ){

    }
}
