package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import repository.EventDao;

import java.util.Arrays;

/**
 * Created by mihkel on 5.04.2018.
 */


@SpringBootApplication
public class Application {

    @Autowired
    private EventDao eventDao;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {


        Event event = new Event("Saariselkä 2018");
        event.addMember("Mihkel Märtin","Miku","mihkelmartin@gmail.com","");
        event.addMember("Alvar Tõruke","Tõru","alvar@gmai.com","");
        event.addMember("Peeter Kutman","Peta","","");
        event.addMember("Tõnu Riisalo","Tõnu","","");
        event.addMember("Lauri Maisvee","Lauri","","");
        event.addTransaction("Taksosõit Ivalost Saariselkä");
        event.addTransaction("Kolmapäevane I poeskäik");
        System.out.println(event.toString());
        eventDao.add(event);

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
