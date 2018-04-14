

import model.AppConfig;
import model.Event;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import service.EventService;

/**
 * Created by mihkel on 9.04.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class EventTest {

    @Autowired
    @Qualifier("mongoOperations")
    private MongoOperations mongoOperations;

    @Autowired
    EventService eventService;

    @Before public void initialize(){
        mongoOperations.dropCollection(Event.class);
    }

    @Test
    public void EventBasics(){
        Event event = eventService.add("Saariselkä 2018");
        String eventid = event.getId();
        assertNotNull(event);
        assertNotNull(eventid);

        Event fromDb = eventService.loadEvent(eventid);
        assertNotNull(fromDb);
        assertEquals (event.getId(), fromDb.getId());
        assertEquals (fromDb.getName(), "Saariselkä 2018");
        assertEquals (event.getName(), fromDb.getName());

        eventService.save(fromDb,"Saariselkä 2019");
        eventid = fromDb.getId();

        Event fromDb2 = eventService.loadEvent(eventid);
        assertNotNull(fromDb2);
        assertEquals (fromDb.getId(), fromDb2.getId());
        assertEquals (fromDb2.getName(), "Saariselkä 2019");
        assertEquals (fromDb.getName(), fromDb2.getName());

        eventService.remove(fromDb2);

        Event fromDb3 = eventService.loadEvent(eventid);
        assertNull(fromDb3);
    }
}
