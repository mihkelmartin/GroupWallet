package service;

import model.Event;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mihkel on 4.06.2018.
 */
public class LoginService {

    @Autowired
    EventService eventService;

    @Autowired
    EmailServiceImpl emailService;


    static final short maxFailedPINLogins = 3;

    public boolean loginPIN(Event event, Long PIN){
        boolean retVal = false;

        if(event.getFailedLogins() < maxFailedPINLogins){
           if(event.getPIN().equals(PIN)){
               event.setFailedLogins((short)0);
               eventService.save(event, event);
               retVal = true;
           } else {
               event.setFailedLogins((short) (event.getFailedLogins() + 1));
               eventService.save(event, event);
               retVal = false;
           }

           if(event.getFailedLogins() == maxFailedPINLogins){
               event.generatePUK();
               eventService.save(event, event);
               emailService.sendSimpleMessage(event.getOwnerEmail(),
                       event.getName() + " : PUK:",event.getPIN().toString());
           }
        }
        return retVal;
    }

    public boolean resetPIN(Event event, Long PUK){
        boolean retVal = false;
        if(event.getFailedLogins() >= maxFailedPINLogins) {
            if (event.getPIN().equals(PUK)) {
                event.setFailedLogins((short) 0);
                event.generatePIN();
                eventService.save(event, event);
                emailService.sendSimpleMessage(event.getOwnerEmail(),
                        event.getName() + " : New PIN:", event.getPIN().toString());
            }
        }
        return retVal;
    }
}
