package com.hand.training;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hand.training.mapper")
public class SpringBootTrainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTrainApplication.class, args);
    }
}