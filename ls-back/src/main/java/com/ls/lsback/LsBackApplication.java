package com.ls.lsback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LsBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsBackApplication.class, args);
    }

}
