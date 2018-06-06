package aspects;

import model.Member;
import model.Transaction;
import model.TransactionItem;
import service.TransactionService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mihkel on 10.04.2018.
 */

@Aspect
public class MoneyCalculationAspect {

    @Autowired
    private TransactionService transactionService;

    @AfterReturning(value="(execution(* service.TransactionService.addDebitForMember(..)) || " +
            "execution(* service.TransactionService.addCreditForMember(..)) || " +
            "execution(* service.TransactionService.setAutoCalculationForMember(..)))" +
            " && target(bean)",
    returning = "retVal")
    public void doCalculationTransaction(JoinPoint jp, TransactionService bean, TransactionItem retVal) {
        bean.calculateCredits(retVal.getTransaction());
    }

    @AfterReturning(value="execution(* service.MemberService.add(..)) || " +
            "execution(* service.MemberService.remove(..))",
    returning = "retVal")
    public void doCalculationEventTransactions(JoinPoint jp, Member retVal) {
        for(Transaction transaction : retVal.getEvent().getTransactions()){
            transactionService.calculateCredits(transaction);
        }
    }

}

