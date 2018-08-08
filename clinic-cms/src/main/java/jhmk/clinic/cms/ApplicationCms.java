package jhmk.clinic.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@ComponentScan(basePackages = {"jhmk.clinic"})
public class ApplicationCms {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationCms.class);
        app.run(args);
    }
}