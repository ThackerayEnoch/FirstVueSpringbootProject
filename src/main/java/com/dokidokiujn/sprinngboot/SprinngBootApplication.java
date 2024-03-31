package com.dokidokiujn.sprinngboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dokidokiujn.sprinngboot.mapper")
public class SprinngBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SprinngBootApplication.class, args);
    }

}
