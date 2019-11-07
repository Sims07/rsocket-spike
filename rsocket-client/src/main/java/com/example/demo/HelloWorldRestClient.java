package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import io.micrometer.core.annotation.Timed;
import reactor.core.publisher.Flux;

/**
 * @author djz4712
 */
@RestController
@Timed(histogram = true)
public class HelloWorldRestClient {
    private final RSocketRequester rSocketRequester;
    private final WebClient webClientMvc;
    private final WebClient webClientReactor;

    public HelloWorldRestClient(RSocketRequester rSocketRequester,WebClient webClientMvc,WebClient webClientReactor){
        this.webClientMvc=webClientMvc;
        this.webClientReactor=webClientReactor;
        this.rSocketRequester = rSocketRequester;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxGetTextBasic() {
        return Flux.just("TEST");
    }

    @GetMapping(value = "/mvc", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String getTextBasic() {
        return "TEST";
    }

    @GetMapping(value = "/flux/mvc/blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxWebClientWithBlockingCall() {
        return webClientMvc.get().uri("slow/test").retrieve().bodyToFlux(String.class);
    }

    @GetMapping(value = "/flux/mvc/fast", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxWebClientWithFastCall() {
        return webClientMvc.get().uri("http://spring-mvc-server:8082/fast1/test").retrieve().bodyToFlux(String.class);
    }

    @GetMapping(value = "/flux/rsocket/delay/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxRsocketDelay(@PathVariable("name") String name) {
        return rSocketRequester.route("helloWorld").data(name).retrieveFlux(String.class);
    }

    @GetMapping(value = "/flux/flux/delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxFluxDelay() {
        return webClientReactor.get().uri("flux/delay").retrieve().bodyToFlux(String.class);
    }

    @GetMapping(value = "/flux/rsocket/fast", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxRsocketBasic() {
        return rSocketRequester.route("fast").retrieveFlux(String.class);
    }

    @GetMapping(value = "/now/{timeZone}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> now(@PathVariable("timeZone") String timeZone) {
        return rSocketRequester.route("now").data("America/" + timeZone).retrieveFlux(String.class);
    }
}
