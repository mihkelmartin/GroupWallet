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
    public void MemberSingleAdd(){

        // Creation
        Event event = eventService.add("Saariselkä 2019");
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        String mihkelid = mihkel.getId();

        assertNotNull(mihkel);
        assertNotNull(mihkelid);
        assertEquals(event.getMembers().size(),1);

        // Reload
        event = eventService.loadEvent(event.getId());
        mihkel = eventService.findMember(event, mihkelid);
        assertNotNull(mihkel);
        assertEquals (mihkelid, mihkel.getId());
        assertEquals(mihkel.getName(),"Mihkel Märtin");
        assertEquals(mihkel.getNickName(),"Miku");
        assertEquals(mihkel.geteMail(),"mihkelmartin@gmail.com");
        assertEquals(mihkel.getBankAccount(),"");
        assertEquals(event.getMembers().size(),1);
    }

    @Test
    public void MemberSingleChange(){

        Event event = eventService.add("Saariselkä 2019");
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        String mihkelid = mihkel.getId();
        memberService.save(mihkel,"Mihkel Kaarli poeg Märtin", "Mikuke","kaubavagun@gmail.com","EESwed");

        event = eventService.loadEvent(event.getId());
        mihkel = eventService.findMember(event, mihkelid);
        assertEquals(mihkel.getName(),"Mihkel Kaarli poeg Märtin");
        assertEquals(mihkel.getNickName(),"Mikuke");
        assertEquals(mihkel.geteMail(),"kaubavagun@gmail.com");
        assertEquals(mihkel.getBankAccount(),"EESwed");

    }

    @Test
    public void MemberRemove(){
        Event event = eventService.add("Saariselkä 2019");
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        Member peeter = memberService.add(event,"Peeter Kutman","Peta","","");
        Member tonu = memberService.add(event,"Tõnu Riisalo","Tõnu","","");
        Member lauri = memberService.add(event,"Lauri Maisvee","Lauri","","");
        String mihkelid = mihkel.getId(), peeterid = peeter.getId(), tonuid = tonu.getId(), lauriid = lauri.getId();

        assertEquals(event.getMembers().size(),4);

        // Remove one
        memberService.remove(peeter);
        Collections.reverse(event.getMembers());

        assertEquals(event.getMembers().size(),3);
        assertEquals(event.getMembers().get(0).getOrder(),3);

        event = eventService.loadEvent(event.getId());
        peeter = eventService.findMember(event, peeterid);
        mihkel = eventService.findMember(event, mihkelid);
        tonu = eventService.findMember(event, tonuid);
        lauri = eventService.findMember(event, lauriid);
        Collections.reverse(event.getMembers());

        assertNull(peeter);
        assertNotNull(mihkel);
        assertNotNull(tonu);
        assertNotNull(lauri);
        assertEquals(event.getMembers().size(),3);
        assertEquals(event.getMembers().get(0).getOrder(),3);

        // Remove all
        memberService.remove(mihkel);
        memberService.remove(tonu);
        memberService.remove(lauri);

        assertEquals(event.getMembers().size(),0);

        event = eventService.loadEvent(event.getId());

        assertEquals(event.getMembers().size(),0);
    }

}
