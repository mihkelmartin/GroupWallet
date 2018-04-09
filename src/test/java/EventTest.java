
import model.AppConfig;
import model.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import repository.EventDao;

/**
 * Created by mihkel on 9.04.2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppConfig.class)
public class EventTest {

    @Autowired
    private EventDao eventDao;

    @Test
    public void plainEvent(){
        Event event = new Event("Saariselkä 2018");
        eventDao.save(event);
    }

}
