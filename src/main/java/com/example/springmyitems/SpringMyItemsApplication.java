package com.example.springmyitems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringMyItemsApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringMyItemsApplication.class, args);
    }

}
