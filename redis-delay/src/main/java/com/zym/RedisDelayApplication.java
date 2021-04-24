package com.zym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.zym.delay")
public class RedisDelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDelayApplication.class, args);
    }

}
