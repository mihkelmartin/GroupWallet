package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.EventService;
import service.MemberService;
import service.TransactionService;

import java.util.*;


/**
 * Created by mihkel on 5.04.2018.
 */


@SpringBootApplication
@Controller
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

    @RequestMapping(value={"/","Event"})
    String home() {
        Event event = eventService.add("Muremõtted 2019");
        Member mihkel = memberService.add(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        memberService.add(event,"Alvar Tõruke","Tõru","alvar@gmail.com","");
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

        return "index.html";
    }


    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/find/email/{email}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Event> findEventsByEmail(@PathVariable String email) throws JsonProcessingException {
        return eventService.loadEventsByEmail(email);
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/event/{eventid}/PIN/{pin}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Event findEvent(@PathVariable String eventid, @PathVariable  Short pin) {
        Event retVal = null;
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getPIN().equals(event.getPIN()))
                retVal = event;

        return retVal;
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Members/Event/{eventid}/PIN/{pin}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Collection<Member> GetEvents(@PathVariable String eventid, @PathVariable  Short pin) {
        Collection<Member> retVal = null;
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getPIN().equals(event.getPIN()))
                retVal = event.getMembers();
        return retVal;
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Transactions/Event/{eventid}/PIN/{pin}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Collection<Transaction> GetTransactions(@PathVariable String eventid, @PathVariable  Short pin) {
        Collection<Transaction> retVal = null;
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getPIN().equals(event.getPIN()))
                retVal = event.getTransactions();
        return retVal;
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/add/{name}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Event addEvent(@PathVariable String name) {
        Event event = eventService.add(name);
        return event;

    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/update/{eventid}/name/{name}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Event updateEvent(@PathVariable String eventid, @PathVariable String name) {
        Event event = eventService.loadEvent(eventid);
        eventService.save(event, name);
        return event;

    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/remove/{eventid}")
    @ResponseBody
    public void removeEvent(@PathVariable String eventid) {
        Event event = eventService.loadEvent(eventid);
        eventService.remove(event);
    }

}