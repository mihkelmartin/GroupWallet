package aspects;

import model.Event;
import model.Member;
import model.Transaction;
import model.TransactionItem;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import repository.EventDao;
import repository.MemberDao;
import repository.TransactionDao;

/**
 * Created by mihkel on 10.04.2018.
 */
@Aspect
@Order(10)
public class DAOAspectMongoDB {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private TransactionDao transactionDao;

    // Event
    @AfterReturning(value="(execution(* service.EventService.add(..)) || " +
                           "execution(* service.EventService.save(..)))",
                    returning="retVal")
    public void changeEvent(JoinPoint jp, Event retVal) {
        eventDao.save(retVal);
        System.out.println("Running DAOAspectMongoDB Event add/save " + jp.getSignature());
    }

    @AfterReturning(value="execution(* service.EventService.remove(..))",
            returning="retVal")
    public void removeEvent(JoinPoint jp, Event retVal) {

        System.out.println("Running DAOAspectMongoDB Event remove " + jp.getSignature());
        eventDao.remove(retVal);
    }

    // Member
    @AfterReturning(value="(execution(* service.MemberService.add(..)) || " +
                          "execution(* service.MemberService.save(..)) || " +
                          "execution(* service.MemberService.remove(..)))",
            returning="retVal")
    public void changeMember(JoinPoint jp, Member retVal) {

        System.out.println("Running DAOAspectMongoDB on Member add/save/remove " + jp.getSignature());
        memberDao.save(retVal);
    }


    // Transaction
    @AfterReturning(value="(execution(* service.TransactionService.add(..)) || " +
                          "execution(* service.TransactionService.save(..)) || " +
                          "execution(* service.TransactionService.remove(..)))",
            returning="retVal")
    public void changeTransaction(JoinPoint jp, Transaction retVal) {

        System.out.println("Running DAOAspectMongoDB on Transaction " + jp.getSignature());
        transactionDao.add(retVal);
    }

    @AfterReturning(value="(execution(* service.TransactionService.addDebitForMember(..)) || " +
            "execution(* service.TransactionService.addCreditForMember(..)) || " +
            "execution(* service.TransactionService.setAutoCalculationForMember(..)))",
            returning="retVal")
    public void changeTransactionItem(JoinPoint jp, TransactionItem retVal) {

        System.out.println("Running DAOAspectMongoDB on TransactionItem " + jp.getSignature());
        transactionDao.add(retVal.getTransaction());
    }

}

