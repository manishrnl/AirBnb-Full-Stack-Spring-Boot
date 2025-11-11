package org.example.tutorial_2_homework.manish_airbnb_clone;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        System.out.println("✅ CORS config loaded");

        SpringApplication.run(MainApplication.class, args);
    }

}
