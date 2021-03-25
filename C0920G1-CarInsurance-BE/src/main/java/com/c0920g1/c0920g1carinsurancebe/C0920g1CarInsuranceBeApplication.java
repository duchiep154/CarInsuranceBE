package com.c0920g1.c0920g1carinsurancebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
@ComponentScan("com.c0920g1")
public class C0920g1CarInsuranceBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(C0920g1CarInsuranceBeApplication.class, args);
    }
}
