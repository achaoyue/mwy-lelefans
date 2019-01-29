package com.lelefans.mwy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.lelefans.mwy.dao")
public class MwyLelefansApplication {

    public static void main(String[] args) {
        SpringApplication.run(MwyLelefansApplication.class, args);
    }

}

