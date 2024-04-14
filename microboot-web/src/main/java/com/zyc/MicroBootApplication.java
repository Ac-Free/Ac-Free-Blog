package com.zyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan("com.zyc.mapper")
public class MicroBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroBootApplication.class,args);
    }
}