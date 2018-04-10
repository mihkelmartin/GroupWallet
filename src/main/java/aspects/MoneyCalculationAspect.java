package aspects;

import model.Event;
import model.Transaction;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;


/**
 * Created by mihkel on 10.04.2018.
 */
@Aspect
public class MoneyCalculationAspect {

    @After(value="(execution(* model.Transaction.addTransactionItem(..)) || " +
            "execution(* model.Transaction.removeTransactionItemsWithMember(..)) || " +
            "execution(* model.Transaction.addDebitForMember(..)) || " +
            "execution(* model.Transaction.addCreditForMember(..)) || " +
            "execution(* model.Transaction.setAutoCalculationOnForMember(..)))" +
            " && target(bean)")
    public void doCalculationTransaction(Object bean) {
        System.out.println("Running aspect on transaction " + ((Transaction)bean).getOrder());
        ((Transaction)bean).calculateCredits();
    }

    @After(value="(execution(* model.Event.addMember(..)) || " +
            "execution(* model.Event.removeMember(..)))" +
            " && target(bean)")
    public void doCalculationEventTransactions(Object bean) {
        System.out.println("Running aspect on Event transactions " + ((Event)bean).toString());
        for(Transaction transaction : ((Event)bean).getTransactions()){
            transaction.calculateCredits();
        }
    }

}

