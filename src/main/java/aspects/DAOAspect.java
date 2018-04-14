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
import repository.TransactionItemDao;


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
    @Autowired
    private TransactionItemDao transactionItemDao;

    // Event
    @AfterReturning(value="execution(* service.EventFactory.add(..))",
                    returning="retVal")
    public void addEvent(JoinPoint jp, Event retVal) {
        eventDao.add(retVal);
        System.out.println("Running DAOAspect Event add " + jp.getSignature());
    }

    @AfterReturning(value="execution(* service.EventFactory.save(..))",
            returning="retVal")
    public void saveEvent(JoinPoint jp, Event retVal) {

        System.out.println("Running DAOAspect Event save " + jp.getSignature());
        eventDao.save(retVal);
    }

    @AfterReturning(value="execution(* service.EventFactory.remove(..))",
            returning="retVal")
    public void removeEvent(JoinPoint jp, Event retVal) {

        System.out.println("Running DAOAspect Event remove " + jp.getSignature());
        eventDao.remove(retVal);
    }

    // Member
    @AfterReturning(value="execution(* service.MemberFactory.add(..))",
            returning="retVal")
    public void addMember(JoinPoint jp, Member retVal) {

        System.out.println("Running DAOAspect on Member add " + jp.getSignature());
        memberDao.add(retVal);
    }

    @AfterReturning(value="execution(* service.MemberFactory.save(..))",
            returning="retVal")
    public void saveMember(JoinPoint jp, Member retVal) {

        System.out.println("Running DAOAspect on Member save " + jp.getSignature());
        memberDao.save(retVal);
    }

    @AfterReturning(value="execution(* service.MemberFactory.remove(..))",
            returning="retVal")
    public void removeMember(JoinPoint jp, Member retVal) {

        System.out.println("Running DAOAspect on Member remove " + jp.getSignature());
        memberDao.remove(retVal);
    }


    // Transaction
    @AfterReturning(value="execution(* service.TransactionFactory.add(..))",
            returning="retVal")
    public void addTransaction(JoinPoint jp, Transaction retVal) {

        System.out.println("Running DAOAspect on Transaction add " + jp.getSignature());
        transactionDao.add(retVal);
    }

    @AfterReturning(value="execution(* service.TransactionFactory.save(..))",
            returning="retVal")
    public void saveTransaction(JoinPoint jp, Transaction retVal) {

        System.out.println("Running DAOAspect on Transaction save " + jp.getSignature());
        transactionDao.save(retVal);
    }

    @AfterReturning(value="execution(* service.TransactionFactory.remove(..))",
            returning="retVal")
    public void removeTransaction(JoinPoint jp, Transaction retVal) {

        System.out.println("Running DAOAspect on Transaction remove " + jp.getSignature());
        transactionDao.remove(retVal);
    }

    // TransactionItem
    @AfterReturning(value="execution(* service.TransactionItemFactory.add(..))",
            returning="retVal")
    public void addTransactionItem(JoinPoint jp, TransactionItem retVal) {

        System.out.println("Running DAOAspect on TransactionItem add " + jp.getSignature());
        transactionItemDao.add(retVal);
    }

    @AfterReturning(value="execution(* service.TransactionItemFactory.save(..))",
            returning="retVal")
    public void updateTransactionItem(JoinPoint jp, TransactionItem retVal) {

        System.out.println("Running DAOAspect on TransactionItem save " + jp.getSignature());
        transactionItemDao.save(retVal);
    }

    @AfterReturning(value="execution(* service.TransactionItemFactory.remove(..))",
            returning="retVal")
    public void removeTransactionItemFromTransaction(JoinPoint jp, TransactionItem retVal) {

        System.out.println("Running DAOAspect on TransactionItem remove " + jp.getSignature());
        transactionItemDao.remove(retVal);
    }

}

