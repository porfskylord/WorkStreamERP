package com.wserp.apigateway.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/authFallback")
    public Mono<String> authFallback() {
        return Mono.just("Auth Service is taking too long to respond or is down. Please try again later.");
    }

    @RequestMapping("/userFallback")
    public Mono<String> userFallback() {
        return Mono.just("User Service is taking too long to respond or is down. Please try again later.");
    }
}
