package com.julian.springbacken;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@MapperScan("com.julian.springbacken.mapper")
@EnableKafka
public class SpringBackenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBackenApplication.class, args);
    }

}
