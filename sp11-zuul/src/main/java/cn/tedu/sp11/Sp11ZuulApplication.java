package cn.tedu.sp11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@EnableZuulProxy
@SpringBootApplication
public class Sp11ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp11ZuulApplication.class, args);
    }

}
