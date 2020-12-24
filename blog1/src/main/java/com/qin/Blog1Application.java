package com.qin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qin.mapper")
public class Blog1Application {

    public static void main(String[] args) {
        SpringApplication.run(Blog1Application.class, args);
    }
        //测试版本恢复
}
