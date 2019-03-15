package com.example.demo.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    System.out.println("elasticsearch start...");
    SpringApplication.run(Application.class);
    System.out.println("elasticsearch end...");
  }
}
