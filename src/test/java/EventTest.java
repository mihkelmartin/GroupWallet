

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

/**
 * Created by mihkel on 9.04.2018.
 */

@EnableAspectJAutoProxy(proxyTargetClass=true)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class EventTest {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private Event event;


    @Test
    public void plainEvent(){
        event.update("Saariselkä 2019");
        Member mihkel = event.addMember("Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        event.addMember("Alvar Tõruke","Tõru","alvar@gmai.com","");
        Transaction transaction = event.addTransaction("Kolmapäevane I poeskäik");
        transaction.addDebitForMember(mihkel.getId(), 320);

        Event fromDb = eventDao.findEvent(event.getId());
        assertEquals (fromDb.getName(), "Saariselkä 2019");
        fromDb.update("Saariselkä 2010");
        fromDb = eventDao.findEvent(event.getId());
        assertEquals (fromDb.getName(), "Saariselkä 2010");

    }

}
