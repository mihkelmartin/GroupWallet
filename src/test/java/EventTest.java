

import model.AppConfig;
import model.Event;
import model.Member;
import model.Transaction;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;
import repository.EventDao;
import service.EventService;
import service.MemberService;
import service.TransactionService;

/**
 * Created by mihkel on 9.04.2018.
 */

@EnableAspectJAutoProxy(proxyTargetClass=true)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class EventTest {

    @Autowired
    EventService eventService;
    @Autowired
    MemberService memberService;
    @Autowired
    TransactionService transactionService;


    @Test
    public void plainEvent(){
        Event event = eventService.createNew("Saariselkä 2019");
        Member mihkel = memberService.createNew(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        Member alvar = memberService.createNew(event, "Alvar Tõruke","Tõru","alvar@gmai.com","");
        Transaction transaction = transactionService.createNew(event, "Kolmapäevane I poeskäik", false);
        transactionService.addDebitForMember(transaction, mihkel, 320);

        Event fromDb = eventService.findEvent("id", event.getId());
        assertEquals (fromDb.getName(), "Saariselkä 2019");
        fromDb.update("Saariselkä 2010");
        fromDb = eventService.findEvent("id", event.getId());
//        assertEquals (fromDb.getName(), "Saariselkä 2010");

    }

}
