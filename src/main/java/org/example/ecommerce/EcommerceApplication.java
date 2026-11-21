package org.example.ecommerce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 这堆东西都是根据官方文档做的，暂时不知道是什么没关系
@SpringBootApplication
@MapperScan("org.example.ecommerce")     // 包名，不要写成其它包
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

}
