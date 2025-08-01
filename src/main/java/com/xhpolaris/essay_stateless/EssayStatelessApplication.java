package com.xhpolaris.essay_stateless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EssayStatelessApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EssayStatelessApplication.class);
        app.run(args);
    }

}
