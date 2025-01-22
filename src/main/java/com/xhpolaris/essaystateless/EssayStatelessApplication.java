package com.xhpolaris.essaystateless;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EssayStatelessApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EssayStatelessApplication.class);
        app.run(args);
    }

}
