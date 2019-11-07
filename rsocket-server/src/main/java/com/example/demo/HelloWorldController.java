package com.example.demo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import io.micrometer.core.annotation.Timed;
import reactor.core.publisher.Flux;

/**
 * @author djz4712
 */
@Controller
@Timed
public class HelloWorldController {

    @MessageMapping("helloWorld")
    public Flux<String> helloWorld(String name) {
        return Flux.just("1", "2", "3", "4", "5", "6",name).delayElements(Duration.ofMillis(1000));
    }

    @MessageMapping("fast")
    public Flux<String> fast() {
        return Flux.just("1", "2", "3", "4", "5", "6", "7");
    }

    @MessageMapping("now")
    public Flux<String> now(String timeZone) {
        return Flux.fromStream( Stream.generate(String::new)).map(s -> {
            ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.of(timeZone));
            ZonedDateTime zonedDateTimeUTC = LocalDateTime.now().atZone(ZoneId.of("UTC"));
            return timeZone + ":" + displayDateWithEpoch(zonedDateTime) + "//UTC" + displayDateWithEpoch(zonedDateTimeUTC);
        }).delayElements(Duration.ofSeconds(2));
    }

    private String displayDateWithEpoch(ZonedDateTime zonedDateTimeUTC) {
        return zonedDateTimeUTC.toString() + "----" + zonedDateTimeUTC.toEpochSecond();
    }
}
