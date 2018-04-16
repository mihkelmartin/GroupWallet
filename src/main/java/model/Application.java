package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import service.EventService;
import service.MemberService;
import service.TransactionService;


import java.util.Arrays;


/**
 * Created by mihkel on 5.04.2018.
 */



@SpringBootApplication
@RestController
public class Application {

    @Autowired
    EventService eventService;
    @Autowired
    MemberService memberService;
    @Autowired
    TransactionService transactionService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    String home() {
        Event event = eventService.add("Saariselkä 2018");
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        memberService.add(event,"Alvar Tõruke","Tõru","alvar@gmai.com","");
        memberService.add(event,"Peeter Kutman","Peta","","");
        Member tonu = memberService.add(event,"Tõnu Riisalo","Tõnu","","");
        Member lauri = memberService.add(event,"Lauri Maisvee","Lauri","","");
        Transaction kustutatav = transactionService.add(event,"Taksosõit Ivalost Saariselkä", false);
        Transaction transaction = transactionService.add(event,"Kolmapäevane I poeskäik", false);
        transactionService.addDebitForMember(transaction, lauri, 320);
        transactionService.addDebitForMember(transaction, tonu, 225);
        transactionService.addCreditForMember(transaction, mihkel, 0);
        transactionService.addDebitForMember(transaction, tonu, 0);
        transactionService.setAutoCalculationForMember(transaction, mihkel, false);
        Member munajoodik = memberService.add(event,"Munajoodik Tuslik","Tuslik","kaarelmartin@gmail.com","");
        memberService.remove(tonu);
        memberService.save(lauri,"Lauri Moss", "Lauri", "maisvee@gmail.com","EE124141242");
        transactionService.remove(kustutatav);

        return event.getId();
    }
    @GetMapping(path = "/Event/{eventid}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Event findEvent(@PathVariable String eventid) {
        Event event = eventService.loadEvent(eventid);
        return event;

    }
}
