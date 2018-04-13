package aspects;

import model.Member;
import model.Transaction;
import model.TransactionItem;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import service.TransactionService;


/**
 * Created by mihkel on 10.04.2018.
 */
@Aspect
@Order(20)
public class MoneyCalculationAspect {

    @Autowired
    private TransactionService transactionService;

    @AfterReturning(value="(execution(* service.TransactionService.addDebitForMember(..)) || " +
            "execution(* service.TransactionService.addCreditForMember(..)) || " +
            "execution(* service.TransactionService.setAutoCalculationForMember(..)))" +
            " && target(bean)",
    returning = "retVal")
    public void doCalculationTransaction(JoinPoint jp, TransactionService bean, TransactionItem retVal) {
        System.out.println("Running Calculation aspect "  + jp.getSignature());
        bean.calculateCredits(retVal.getTransaction());
    }

    @AfterReturning(value="execution(* service.MemberService.add(..)) || " +
            "execution(* service.MemberService.remove(..))",
    returning = "retVal")
    public void doCalculationEventTransactions(JoinPoint jp, Member retVal) {
        System.out.println("Running Calculation aspect on Event "  + jp.getSignature());
        for(Transaction transaction : retVal.getEvent().getTransactions()){
            transactionService.calculateCredits(transaction);
        }
    }

}

