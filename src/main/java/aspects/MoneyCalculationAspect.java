package aspects;

import model.Event;
import model.Transaction;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;


/**
 * Created by mihkel on 10.04.2018.
 */
@Aspect
@Order(20)
public class MoneyCalculationAspect {

    @After(value="(execution(* model.Transaction.addDebitForMember(..)) || " +
            "execution(* model.Transaction.addCreditForMember(..)) || " +
            "execution(* model.Transaction.setAutoCalculationOnForMember(..)))" +
            " && target(bean)")
    public void doCalculationTransaction(Object bean) {
        System.out.println("Running Calculation aspect on Transaction " + ((Transaction)bean).getOrder());
    }

    @After(value="(execution(* model.Event.addMember(..)) || " +
            "execution(* model.Event.removeMember(..)))" +
            " && target(bean)")
    public void doCalculationEventTransactions(Object bean) {
        System.out.println("Running Calculation aspect on Event " + ((Event)bean).toString());
    }

}

