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

import java.util.Collections;

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
    private Member mihkel, alvar, peeter, tonu;
    private String mihkelid, alvarid, peeterid, tonuid;
    private Transaction taksosoit, poeskaik, saanisoit;
    private String eventid, taksoid, poesid, saanid;
    String taksoname = "Taksosõit Ivalost Saariselkä";
    String poesname = "Kolmapäevane I poeskäik";
    String saanisoitname = "Saanisõit";
    String taksonameChg = "Taksosõit Ivalo lennujaamast Saariselkä";
    String poesnameChg = "Kolmapäevane poeskäik";
    String saanisoitnameChg = "Saanisõit kambaga";
    private double delta = 0.001;


    @Before
    public void initialize(){
        mongoOperations.dropCollection(Event.class);
        event = eventService.add("Saariselkä 2019");
        eventid = event.getId();
        mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        alvar = memberService.add(event, "Alvar Tõruke","Tõru","alvar@gmai.com","");
        peeter = memberService.add(event,"Peeter Kutman","Peta","","");
        tonu = memberService.add(event,"Tõnu Riisalo","Tõnu","","");
        mihkelid = mihkel.getId();
        alvarid = alvar.getId();
        peeterid = peeter.getId();
        tonuid = tonu.getId();

        taksosoit = transactionService.add(event,taksoname, false);
        poeskaik = transactionService.add(event,poesname, false);
        saanisoit = transactionService.add(event,saanisoitname, false);
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

        assertEquals(taksosoit.getId(), taksoid);
        assertEquals(poeskaik.getId(), poesid);
        assertEquals(saanisoit.getId(), saanid);

        assertEquals(taksosoit.getName(), taksoname);
        assertEquals(poeskaik.getName(), poesname);
        assertEquals(saanisoit.getName(), saanisoitname);

        assertEquals(event.getMembers().size(), 4);
        assertEquals(event.getTransactions().size(), 3);

        assertEquals(taksosoit.getItems().size(),event.getMembers().size());
        assertEquals(poeskaik.getItems().size(), event.getMembers().size());
        assertEquals(saanisoit.getItems().size(), event.getMembers().size());
    }

    @Test
    public void TransactionChange() {
        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);
        poeskaik = eventService.findTransaction(event, poesid);
        saanisoit = eventService.findTransaction(event, saanid);
        transactionService.save(taksosoit,taksonameChg, false);
        transactionService.save(poeskaik,poesnameChg, false);
        transactionService.save(saanisoit,saanisoitnameChg, false);

        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);
        poeskaik = eventService.findTransaction(event, poesid);
        saanisoit = eventService.findTransaction(event, saanid);
        assertNotNull(taksosoit);
        assertNotNull(poeskaik);
        assertNotNull(saanisoit);
        assertEquals(taksosoit.getName(), taksonameChg);
        assertEquals(poeskaik.getName(), poesnameChg);
        assertEquals(saanisoit.getName(), saanisoitnameChg);
    }

    @Test
    public void TransactionWithoutMembers() {
        mongoOperations.dropCollection(Event.class);
        event = eventService.add("Saariselkä 2020");
        eventid = event.getId();
        taksosoit = transactionService.add(event,taksoname, false);
        taksoid = taksosoit.getId();

        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);

        assertNotNull(taksosoit);
        assertEquals(taksosoit.getName(), taksoname);
        assertEquals(event.getTransactions().size(), 1);


    }

    @Test
    public void TransactionRemoveTransaction() {
        // Remove one
        transactionService.remove(poeskaik);
        Collections.reverse(event.getTransactions());

        assertEquals(2, event.getTransactions().size());
        assertEquals(2, event.getTransactions().get(0).getOrder());

        event = eventService.loadEvent(eventid);
        poeskaik = eventService.findTransaction(event, poesid);
        taksosoit = eventService.findTransaction(event, taksoid);
        saanisoit = eventService.findTransaction(event, saanid);
        Collections.reverse(event.getTransactions());

        assertNull(poeskaik);
        assertEquals(2, event.getTransactions().size());
        assertEquals(2, event.getTransactions().get(0).getOrder());

        // Remove all
        transactionService.remove(taksosoit);
        transactionService.remove(saanisoit);

        assertEquals(0, event.getTransactions().size());

        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);
        saanisoit = eventService.findTransaction(event, saanid);

        assertNull(taksosoit);
        assertNull(saanisoit);
        assertEquals(0, event.getTransactions().size());

    }

    @Test
    public void TransactionRemoveMember() {
        // Remove two members
        memberService.remove(mihkel);
        memberService.remove(tonu);

        assertEquals(taksosoit.getItems().size(),event.getMembers().size());
        assertEquals(poeskaik.getItems().size(), event.getMembers().size());
        assertEquals(saanisoit.getItems().size(), event.getMembers().size());

        // Reload and test again
        event = eventService.loadEvent(eventid);
        taksosoit = eventService.findTransaction(event, taksoid);
        poeskaik = eventService.findTransaction(event, poesid);
        saanisoit = eventService.findTransaction(event, saanid);

        assertEquals(taksosoit.getItems().size(),event.getMembers().size());
        assertEquals(poeskaik.getItems().size(), event.getMembers().size());
        assertEquals(saanisoit.getItems().size(), event.getMembers().size());

        // Remove all
        peeter = eventService.findMember(event, peeterid);
        alvar = eventService.findMember(event, alvarid);
        memberService.remove(peeter);
        memberService.remove(alvar);

        assertEquals(0, taksosoit.getItems().size());
        assertEquals(0, poeskaik.getItems().size());
        assertEquals(0, saanisoit.getItems().size());

        // Reload and test again
        event = eventService.loadEvent(eventid);
        poeskaik = eventService.findTransaction(event, poesid);
        taksosoit = eventService.findTransaction(event, taksoid);
        saanisoit = eventService.findTransaction(event, saanid);

        assertEquals(0, taksosoit.getItems().size());
        assertEquals(0, poeskaik.getItems().size());
        assertEquals(0, saanisoit.getItems().size());
    }

    @Test
    public void TransactionAddMember() {
        Member lauri = memberService.add(event,"Lauri Maisvee","Lauri","","");
        assertEquals(5, taksosoit.getItems().size());
        assertEquals(5, poeskaik.getItems().size());
        assertEquals(5, saanisoit.getItems().size());

        // Reload and test again
        event = eventService.loadEvent(eventid);
        poeskaik = eventService.findTransaction(event, poesid);
        taksosoit = eventService.findTransaction(event, taksoid);
        saanisoit = eventService.findTransaction(event, saanid);

        assertEquals(5, taksosoit.getItems().size());
        assertEquals(5, poeskaik.getItems().size());
        assertEquals(5, saanisoit.getItems().size());

    }

    @Test
    public void TransactionDebitCalculation(){

    }

    @Test
    public void TransactionCreditCalculation(){
        double dtaksosoit = 62.5;
        transactionService.addDebitForMember(taksosoit, mihkel, dtaksosoit);
        assertEquals(dtaksosoit/mihkel.getEvent().getMembers().size(),
                transactionService.getTransactionItemForMember(taksosoit, mihkel).getCredit(), delta);

    }

    @Test
    public void TransactionAutoCalculationChange(){

    }

    @Test
    public void TransactionAddMemberCalculation(){

    }
    @Test
    public void TransactionRemoveMemberCalculation(){

    }
}
