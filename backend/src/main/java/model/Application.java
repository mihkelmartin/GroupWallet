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

        Member mihkelmock = new Member();
        Member alvarmock = new Member();
        Member peetermock = new Member();
        Member tonumock = new Member();
        Member laurimock = new Member();
        Member munajoodikmock = new Member();
        mihkelmock.setName("Mihkel Märtin"); mihkelmock.setNickName("Miku");mihkelmock.seteMail("mihkelmartin@gmail.com");mihkelmock.setBankAccount("");
        alvarmock.setName("Alvar Tõruke"); alvarmock.setNickName("Tõru");alvarmock.seteMail("alvar@gmail.com");alvarmock.setBankAccount("");
        peetermock.setName("Peeter Kutman"); peetermock.setNickName("Peta");
        tonumock.setName("Tõnu Riisalo"); tonumock.setNickName("Tõnu");
        laurimock.setName("Lauri Maisvee"); laurimock.setNickName("Lauri");
        munajoodikmock.setName("Munajoodik Tuslik"); munajoodikmock.setNickName("Tuslik");munajoodikmock.seteMail("kaarelmartin@gmail.com");

        Transaction taksomock = new Transaction();
        Transaction poesmock = new Transaction();
        taksomock.setName("Taksosõit Ivalost Saariselkä");
        poesmock.setName("Kolmapäevane I poeskäik");

        Event newEvent = new Event();
        newEvent.setName("Muremõtted 2019");
        Event event = eventService.add(newEvent);
        Member mihkel = memberService.add(event,mihkelmock);
        memberService.add(event, alvarmock);
        memberService.add(event, peetermock);
        Member tonu = memberService.add(event, tonumock);
        Member lauri = memberService.add(event, laurimock);
        Transaction kustutatav = transactionService.add(event, taksomock);
        Transaction transaction = transactionService.add(event, poesmock);
        transactionService.addDebitForMember(transaction, lauri, 320);
        transactionService.addDebitForMember(transaction, tonu, 225);
        transactionService.addCreditForMember(transaction, mihkel, 0);
        transactionService.addDebitForMember(transaction, tonu, 0);
        transactionService.setAutoCalculationForMember(transaction, mihkel, false);
        Member munajoodik = memberService.add(event, munajoodikmock);
        memberService.remove(tonu);
        laurimock.setName("Lauri Moss");laurimock.seteMail("maisvee@gmail.com");laurimock.setBankAccount("EE124141242");
        memberService.save(lauri, laurimock);
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
        Event newEvent = new Event();
        newEvent.setName(name);
        Event event = eventService.add(newEvent);
        return event;

    }

    @CrossOrigin(origins = "${clientcors.url}")
    @PostMapping(path = "/Event/update/{token}")
    @ResponseBody
    public void updateEvent(@PathVariable String token, @RequestBody Event updatedEvent) {
        Event event = eventService.loadEvent(updatedEvent.getId());
        eventService.save(event, updatedEvent);
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/remove/{eventid}")
    @ResponseBody
    public void removeEvent(@PathVariable String eventid) {
        Event event = eventService.loadEvent(eventid);
        eventService.remove(event);
    }

}
