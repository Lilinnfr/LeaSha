package com.ls.lsback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class LsBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsBackApplication.class, args);
    }

}
