package com.example.mvc_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.mvc_crud")
public class MvcCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcCrudApplication.class, args);
    }

}
