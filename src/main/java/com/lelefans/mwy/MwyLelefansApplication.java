package com.lelefans.mwy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MwyLelefansApplication {

    public static void main(String[] args) {
        SpringApplication.run(MwyLelefansApplication.class, args);
    }

}

