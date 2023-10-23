package com.julian.springbacken;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.julian.springbacken.mapper")
public class SpringBackenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBackenApplication.class, args);
    }

}
