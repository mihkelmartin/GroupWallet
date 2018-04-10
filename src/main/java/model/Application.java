package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import repository.EventDao;
import repository.MemberDao;
import repository.TransactionDao;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by mihkel on 5.04.2018.
 */


@SpringBootApplication
public class Application {

    @Autowired
    private Event event;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        event.update("Saariselkä 2018");
        Member mihkel = event.addMember("Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        event.addMember("Alvar Tõruke","Tõru","alvar@gmai.com","");
        event.addMember("Peeter Kutman","Peta","","");
        Member tonu = event.addMember("Tõnu Riisalo","Tõnu","","");
        Member lauri = event.addMember("Lauri Maisvee","Lauri","","");
        Transaction kustutatav = event.addTransaction("Taksosõit Ivalost Saariselkä");
        Transaction transaction = event.addTransaction("Kolmapäevane I poeskäik");
        transaction.addDebitForMember(lauri.getId(), 320);
        transaction.addDebitForMember(tonu.getId(), 225);
        transaction.addCreditForMember(mihkel.getId(), 0);
        transaction.addDebitForMember(tonu.getId(), 0);
        transaction.setAutoCalculationOnForMember(mihkel.getId());
        Member munajoodik = event.addMember("Munajoodik Tuslik","Tuslik","kaarelmartin@gmail.com","");
        event.removeMember(tonu);
        lauri.update("Lauri Moss", "Lauri", "maisvee@gmail.com","EE124141242",lauri.getOrder(),lauri.getEvent());
        event.removeTransaction(kustutatav);

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
