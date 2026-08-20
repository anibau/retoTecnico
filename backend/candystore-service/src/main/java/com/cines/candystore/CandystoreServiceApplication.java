package com.cines.candystore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cines")
public class CandystoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CandystoreServiceApplication.class, args);
    }
}
