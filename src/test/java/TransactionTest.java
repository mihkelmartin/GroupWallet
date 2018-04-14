import model.AppConfig;
import model.Event;
import model.Member;
import model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import service.EventService;
import service.MemberService;
import service.TransactionService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by mihkel on 9.04.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class TransactionTest {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOperations;

    @Autowired
    EventService eventService;
    @Autowired
    MemberService memberService;
    @Autowired
    TransactionService transactionService;

    private Event event;
    private Transaction taksosoit, poeskaik, saanisoit;
    private String eventid, taksoid, poesid, saanid;

    @Before
    public void initialize(){
        mongoOperations.dropCollection(Event.class);
        event = eventService.add("Saariselkä 2019");
        eventid = event.getId();
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        Member alvar = memberService.add(event, "Alvar Tõruke","Tõru","alvar@gmai.com","");
        Member peeter = memberService.add(event,"Peeter Kutman","Peta","","");
        Member tonu = memberService.add(event,"Tõnu Riisalo","Tõnu","","");
        taksosoit = transactionService.add(event,"Taksosõit Ivalost Saariselkä", false);
        poeskaik = transactionService.add(event,"Kolmapäevane I poeskäik", false);
        saanisoit = transactionService.add(event,"Saanisõit", false);
        taksoid = taksosoit.getId();
        poesid = poeskaik.getId();
        saanid = saanisoit.getId();
    }

    @Test
    public void TransactionBasics(){
        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);
        poeskaik = eventService.findTransaction(event, poesid);
        saanisoit = eventService.findTransaction(event, saanid);
        assertNotNull(taksosoit);
        assertNotNull(poeskaik);
        assertNotNull(saanisoit);
        assertEquals(event.getMembers().size(), 4);
        assertEquals(event.getTransactions().size(), 3);
        assertEquals(taksosoit.getItems().size(),event.getMembers().size());
        assertEquals(poeskaik.getItems().size(), event.getMembers().size());
        assertEquals(saanisoit.getItems().size(), event.getMembers().size());
    }

    @Test
    public void TransactionUpdate() {
        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);
        poeskaik = eventService.findTransaction(event, poesid);
        saanisoit = eventService.findTransaction(event, saanid);
        assertNotNull(taksosoit);
        assertNotNull(poeskaik);
        assertNotNull(saanisoit);
        assertEquals(taksosoit.getName(), "Taksosõit Ivalost Saariselkä");
        assertEquals(poeskaik.getName(), "Kolmapäevane I poeskäik");
        assertEquals(saanisoit.getName(), "Saanisõit");

        transactionService.save(taksosoit,"Taksosõit Ivalo lennujaamast Saariselkä", false);
        transactionService.save(poeskaik,"Kolmapäevane poeskäik", false);
        transactionService.save(saanisoit,"Saanisõit kambaga", false);

        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);
        poeskaik = eventService.findTransaction(event, poesid);
        saanisoit = eventService.findTransaction(event, saanid);
        assertNotNull(taksosoit);
        assertNotNull(poeskaik);
        assertNotNull(saanisoit);
        assertEquals(taksosoit.getName(), "Taksosõit Ivalo lennujaamast Saariselkä");
        assertEquals(poeskaik.getName(), "Kolmapäevane poeskäik");
        assertEquals(saanisoit.getName(), "Saanisõit kambaga");
    }

    @Test
    public void TransactionWithoutMembers() {
        mongoOperations.dropCollection(Event.class);
        event = eventService.add("Saariselkä 2020");
        eventid = event.getId();
        taksosoit = transactionService.add(event,"Taksosõit Ivalost Saariselkä", false);
        event = eventService.loadEvent(eventid);
        assertEquals(event.getTransactions().size(), 1);


    }
}
