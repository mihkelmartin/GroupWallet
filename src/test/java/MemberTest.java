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
public class MemberTest {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOperations;

    @Autowired
    EventService eventService;
    @Autowired
    MemberService memberService;

    @Before
    public void initialize(){
        mongoOperations.dropCollection(Event.class);
    }

    @Test
    public void MemberBasics(){
        Event event = eventService.add("Saariselkä 2019");
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        String mihkelid = mihkel.getId();

        event = eventService.loadEvent(event.getId());
        mihkel = eventService.findMember(event, mihkelid);
        assertNotNull(mihkel);

        Member alvar = memberService.add(event, "Alvar Tõruke","Tõru","alvar@gmai.com","");
        Member peeter = memberService.add(event,"Peeter Kutman","Peta","","");
        String peeterid = peeter.getId();
        Member tonu = memberService.add(event,"Tõnu Riisalo","Tõnu","","");
        Member lauri = memberService.add(event,"Lauri Maisvee","Lauri","","");
        assertNotNull(mihkel);
        assertEquals(event.getMembers().size(),5);

        memberService.save(mihkel,"Mihkel Kaarli poeg Märtin", "Mikuke","kaubavagun@gmail.com","EESwed");
        Event fromdb = eventService.loadEvent(event.getId());
        Member mihkeldb = eventService.findMember(fromdb, mihkelid);
        assertNotNull(mihkeldb);
        assertEquals(mihkeldb.getName(),"Mihkel Kaarli poeg Märtin");
        assertEquals(mihkeldb.getNickName(),"Mikuke");
        assertEquals(mihkeldb.geteMail(),"kaubavagun@gmail.com");
        assertEquals(mihkeldb.getBankAccount(),"EESwed");

        Member peeterdb = eventService.findMember(fromdb, peeterid);
        assertNotNull(peeterdb);

        memberService.remove(peeterdb);
        assertEquals(fromdb.getMembers().size(),4);
        Collections.reverse(fromdb.getMembers());
        assertEquals(fromdb.getMembers().get(0).getOrder(),4);

        event = eventService.loadEvent(fromdb.getId());
        peeterdb = eventService.findMember(event, peeterid);
        assertNull(peeterdb);
        mihkeldb = eventService.findMember(event, mihkelid);
        assertNotNull(mihkeldb);
        assertEquals(event.getMembers().size(),4);
    }

}
