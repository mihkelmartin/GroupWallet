

import model.AppConfig;
import model.Event;
import model.Member;
import model.Transaction;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import service.EventService;
import service.MemberService;
import service.TransactionService;

/**
 * Created by mihkel on 9.04.2018.
 */

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
        transactionService.addDebitForMember(transaction, alvar, 320);
        String mihkelid = mihkel.getId();
        Event fromDb = eventService.findEvent("id", event.getId());
        assertEquals (fromDb.getName(), "Saariselkä 2019");
        eventService.update(fromDb,"Saariselkä 2010");
        fromDb = eventService.findEvent("id", event.getId());
        assertEquals (fromDb.getName(), "Saariselkä 2010");
        Member mihkeltaastatud = eventService.findMember(fromDb, mihkelid);
        assertNotNull(mihkeltaastatud);
        memberService.remove(mihkeltaastatud);
        transactionService.remove(transaction);
    }

}
