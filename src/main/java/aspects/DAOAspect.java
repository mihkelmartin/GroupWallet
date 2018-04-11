package aspects;

import model.Event;
import model.Member;
import model.Transaction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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
public class DAOAspect {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private TransactionDao transactionDao;



    @AfterReturning(value="execution(* service.EventService.createNew(..))",
                    returning="retVal")
    public void SaveNewEvent(JoinPoint jp, Event retVal) {
        eventDao.save(retVal);
        System.out.println("Running DAOAspect on new Event " + jp.getSignature());
    }

    @After(value="execution(* service.EventService.update(..)) && args(event,..)")
    public void SaveEvent(JoinPoint jp, Event event) {

        System.out.println("Running DAOAspect on Event " + jp.getSignature());
        eventDao.save(event);
    }

    @AfterReturning(value="execution(* service.MemberService.*(..))",
            returning="retVal")
    public void SaveMemberInEvent(JoinPoint jp, Member retVal) {

        System.out.println("Running DAOAspect on Members/Transactions (return Member) " + jp.getSignature());
        memberDao.save(retVal);
    }

    @AfterReturning(value="execution(* service.TransactionService.*(..))",
            returning="retVal")
    public void SaveTransactionInEvent(JoinPoint jp, Transaction retVal) {

        System.out.println("Running DAOAspect Transactions (return Member) " + jp.getSignature());
        transactionDao.save(retVal);
    }

}

