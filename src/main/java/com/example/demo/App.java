package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> hibernateDebugConfigurer() {
    return event -> {
      org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(
        org.hibernate.engine.jdbc.spi.SqlExceptionHelper.class
      );
      if (logger instanceof ch.qos.logback.classic.Logger) {
        ch.qos.logback.classic.Logger hibernateLogger = (ch.qos.logback.classic.Logger) logger;
        hibernateLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);
      }
    };
  }
}
