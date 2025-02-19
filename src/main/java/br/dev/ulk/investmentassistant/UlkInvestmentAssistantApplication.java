package br.dev.ulk.investmentassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UlkInvestmentAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(UlkInvestmentAssistantApplication.class, args);
    }

}