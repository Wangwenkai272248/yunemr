package jhmk.clinic.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@ComponentScan(basePackages = {"jhmk.clinic"})
public class ApplicationEntity {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationEntity.class);
        app.run(args);
    }
}
