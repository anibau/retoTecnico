package com.cines.complete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cines")
public class CompleteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompleteServiceApplication.class, args);
    }
}
