package com.example.software01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.software01.pojo")
@EnableJpaRepositories(basePackages = "com.example.software01.repositories")
public class Software01Application {

    public static void main(String[] args) {
        SpringApplication.run(Software01Application.class, args);
    }

}
