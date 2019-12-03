package com.ramtech;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.vertxbeans.rxjava.VertxBeans;

@SpringBootApplication
@Import(VertxBeans.class)
public class RouterTestMain {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(RouterTestMain.class);
        application.setBannerMode(Banner.Mode.LOG);
        application.run(args);
    }
}