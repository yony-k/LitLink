package com.yonyk.litlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LitLinkApplication {

  public static void main(String[] args) {
    SpringApplication.run(LitLinkApplication.class, args);
  }
}
