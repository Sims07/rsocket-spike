package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

/**
 * @author djz4712
 */
@RestController
@Timed
public class HelloWorldRestController {

    @GetMapping(value = "/fast1/{name}")
    public String fast1(@PathVariable("name") String name) {
        return "TEST";
    }

    @GetMapping(value = "/slow/{name}")
    public String slow(@PathVariable("name") String name) throws InterruptedException {
        Thread.sleep(7000);
        return "TEST";
    }

}
