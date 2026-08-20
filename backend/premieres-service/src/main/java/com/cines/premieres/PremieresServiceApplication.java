package com.cines.premieres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cines")
public class PremieresServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PremieresServiceApplication.class, args);
    }
}
