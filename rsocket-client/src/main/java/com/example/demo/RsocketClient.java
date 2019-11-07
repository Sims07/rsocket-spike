package com.example.demo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class RsocketClient {

    public static void main(String[] args) {
        SpringApplication.run(RsocketClient.class, args);
    }


    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder rsocketRequesterBuilder) {
        return rsocketRequesterBuilder.connectTcp("rsocket-server", 7000).block();
    }

    @Bean
    @Qualifier("webClientMvc")
    WebClient webClientMvc(){
        return WebClient.create("http://spring-mvc-server:8082");
    }

    @Bean
    @Qualifier("webClientReactor")
    WebClient webClientReactor(){
        return WebClient.create("http://rsocket-server:8080");
    }



}
