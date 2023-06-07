package com.example.junit_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JunitBankApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(JunitBankApplication.class, args);
//        String[] beanDefinitionNames = run.getBeanDefinitionNames();
//        for (String beanName : beanDefinitionNames) {
//            System.out.println("beanName : " + beanName);
//        }
    }

}
