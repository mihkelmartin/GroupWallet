package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import service.EventService;
import service.MemberService;
import service.TransactionService;


import java.util.Arrays;


/**
 * Created by mihkel on 5.04.2018.
 */


@SpringBootApplication
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

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        Event event = eventService.createNew("Saariselkä 2018");
        Member mihkel = memberService.createNew(event,"Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        memberService.createNew(event,"Alvar Tõruke","Tõru","alvar@gmai.com","");
        memberService.createNew(event,"Peeter Kutman","Peta","","");
        Member tonu = memberService.createNew(event,"Tõnu Riisalo","Tõnu","","");
        Member lauri = memberService.createNew(event,"Lauri Maisvee","Lauri","","");
        Transaction kustutatav = transactionService.createNew(event,"Taksosõit Ivalost Saariselkä", false);
        Transaction transaction = transactionService.createNew(event,"Kolmapäevane I poeskäik", false);
        transactionService.addDebitForMember(transaction, lauri, 320);
        transactionService.addDebitForMember(transaction, tonu, 225);
        transactionService.addCreditForMember(transaction, mihkel, 0);
        transactionService.addDebitForMember(transaction, tonu, 0);
        transactionService.setAutoCalculationForMember(transaction, mihkel, false);
        Member munajoodik = memberService.createNew(event,"Munajoodik Tuslik","Tuslik","kaarelmartin@gmail.com","");
        memberService.removeMember(tonu);
        memberService.update(lauri,"Lauri Moss", "Lauri", "maisvee@gmail.com","EE124141242",lauri.getOrder());
        transactionService.remove(kustutatav);

        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();

            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

}
