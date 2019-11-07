package com.example.demo;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author djz4712
 */
@RestController
public class FluxRestController {

    @GetMapping(value = "/flux/delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> helloWorld() {
        return Flux.just("1", "2", "3", "4", "5", "6", "7").delayElements(Duration.ofMillis(1000));
    }
}
