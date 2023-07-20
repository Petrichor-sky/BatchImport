package com.example.localtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
@EnableAsync
public class LocalTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalTestApplication.class, args);
    }

}
