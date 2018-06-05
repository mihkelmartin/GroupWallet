package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.*;

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
    @Autowired
    RecaptchaService recaptchaService;
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    LoginService loginService;


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
    @GetMapping(path = "/login/{eventid}/{pin}", produces = "text/plain")
    @ResponseBody
    public String loginEvent(@PathVariable String eventid, @PathVariable  Long pin) {
        String retVal = "";
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(loginService.loginPIN(event, pin)) {
                event.generateToken();
                eventService.save(event, event);
                retVal = event.getSecurityToken();
            }
        return retVal;
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @PostMapping(path = "/Event/add/{ReCAPTCHAToken}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Event addEvent(@PathVariable String ReCAPTCHAToken, @RequestBody Event newEvent) {

        Event event = null;
        if(recaptchaService.verifyRecaptcha("",ReCAPTCHAToken).equals("")){
            event = eventService.add(newEvent);

            // Add default Member
            Member member = new Member();
            member.setName("Member");
            member.seteMail(event.getOwnerEmail());
            memberService.add(event, member);

            // Add default Transaction
            Transaction transaction = new Transaction();
            transaction.setName("1. Spending");
            transactionService.add(event, transaction);

            emailService.sendSimpleMessage(event.getOwnerEmail(), event.getName(),event.getPIN().toString());
        }
        return event;
    }


    @CrossOrigin(origins = "${clientcors.url}")
    @PostMapping(path = "/Event/update/{token}")
    @ResponseBody
    public void updateEvent(@PathVariable String token, @RequestBody Event updatedEvent) {
        Event event = eventService.loadEvent(updatedEvent.getId());
        if(event.getSecurityToken().equals(token))
            eventService.save(event, updatedEvent);
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/load/{eventid}/{token}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Event loadEvent(@PathVariable String eventid, @PathVariable  String token) {
        Event retVal = null;
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getSecurityToken().equals(token))
                retVal = event;

        return retVal;
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/remove/{eventid}/{token}")
    @ResponseBody
    public void removeEvent(@PathVariable String eventid, @PathVariable  String token) {
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getSecurityToken().equals(token))
                eventService.remove(event);
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Event/find/email/{email}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Event> findEventsByEmail(@PathVariable String email) throws JsonProcessingException {
        return eventService.loadEventsByEmail(email);
    }


    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Members/{eventid}/{token}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Collection<Member> GetMembers(@PathVariable String eventid, @PathVariable  String token) {
        Collection<Member> retVal = null;
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getSecurityToken().equals(token))
                retVal = event.getMembers();
        return retVal;
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Members/add/{eventid}/{token}")
    @ResponseBody
    public void AddMember(@PathVariable String eventid, @PathVariable  String token) {
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getSecurityToken().equals(token)) {
                // Add default Member
                Member member = new Member();
                member.setName("New member");
                member.seteMail("new.member@gmail.com");
                memberService.add(event, member);
            }
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Members/remove/{eventid}/{token}/{memberid}")
    @ResponseBody
    public void RemoveMember(@PathVariable String eventid, @PathVariable  String token, @PathVariable  String memberid) {
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getSecurityToken().equals(token)) {
                memberService.remove(eventService.findMember(event, memberid));
            }
    }

    @CrossOrigin(origins = "${clientcors.url}")
    @GetMapping(path = "/Transactions/{eventid}/{token}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Collection<Transaction> GetTransactions(@PathVariable String eventid, @PathVariable  String token) {
        Collection<Transaction> retVal = null;
        Event event = eventService.loadEvent(eventid);
        if(event != null)
            if(event.getSecurityToken().equals(token))
                retVal = event.getTransactions();
        return retVal;
    }





}
