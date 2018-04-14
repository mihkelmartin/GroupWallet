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


    @Before
    public void initialize(){
        mongoOperations.dropCollection(Event.class);
    }

    @Test
    public void TransactionBasics(){
        Event event = eventService.add("Saariselkä 2019");
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        Member alvar = memberService.add(event, "Alvar Tõruke","Tõru","alvar@gmai.com","");
        Member peeter = memberService.add(event,"Peeter Kutman","Peta","","");
        Member tonu = memberService.add(event,"Tõnu Riisalo","Tõnu","","");
        Transaction taksosoit = transactionService.add(event,"Taksosõit Ivalost Saariselkä", false);
        Transaction poeskaik = transactionService.add(event,"Kolmapäevane I poeskäik", false);
        Transaction saanisoit = transactionService.add(event,"Saanisõit", false);
        String taksoid = taksosoit.getId(), poesid = poeskaik.getId(), saanid = saanisoit.getId();

        assertNotNull(taksosoit);
        assertNotNull(poeskaik);
        assertNotNull(saanisoit);
        assertEquals(event.getMembers().size(), 4);
        assertEquals(event.getTransactions().size(), 3);
        assertEquals(taksosoit.getItems().size(),event.getMembers().size());
        assertEquals(poeskaik.getItems().size(), event.getMembers().size());
        assertEquals(saanisoit.getItems().size(), event.getMembers().size());

    }
}
