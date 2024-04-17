package com.ls.lsback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaRepositories("com.ls.lsback")
@EntityScan("com.ls.lsback")
// @EnableJpaAuditing
public class LsBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(LsBackApplication.class, args);
    }

}
