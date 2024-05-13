package com.ls.lsback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories("com.ls.lsback")
@EntityScan("com.ls.lsback")
@ComponentScan(basePackages = {"com.ls"})
@EnableScheduling
// @EnableJpaAuditing
public class LsBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsBackApplication.class, args);
    }

}
