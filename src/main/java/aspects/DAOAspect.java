package aspects;

import model.Event;
import model.Member;
import model.Transaction;
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

    @After(value="(execution(* model.Event.update(..)) || " +
            "execution(model.Event.new(..)))" +
            " && target(bean)")
    public void SaveEvent(Object bean) {

        System.out.println("Running DAOAspect on Event " + ((Event)bean).toString());
        eventDao.save((Event) bean);
    }

    @AfterReturning(value="(execution(* model.Event.addMember(..)) || " +
                 "execution(* model.Event.removeMember(..))) ",
            returning="retVal")
    public void SaveMember(Object retVal) {

        System.out.println("Running DAOAspect on adding/removing Member " + ((Member)retVal).toString());
        memberDao.save((Member) retVal);
    }

    @AfterReturning(value="(execution(* model.Event.addTransaction(..)) || " +
            "execution(* model.Event.removeTransaction(..))) ",
            returning="retVal")
    public void SaveTransactionInEven(Object retVal) {

        System.out.println("Running DAOAspect on adding/removing Transaction " + ((Transaction)retVal).toString());
        transactionDao.save((Transaction) retVal);
    }

    @AfterReturning(value="(execution(* model.Transaction.addDebitForMember(..)) || " +
            "execution(* model.Transaction.addCreditForMember(..)) || " +
            "execution(* model.Transaction.setAutoCalculationOnForMember(..))) " +
            " && target(bean)")
    public void SaveTransaction(Object bean) {

        System.out.println("Running DAOAspect on updating Transaction " + ((Transaction)bean).toString());
        transactionDao.save((Transaction) bean);
    }
}

