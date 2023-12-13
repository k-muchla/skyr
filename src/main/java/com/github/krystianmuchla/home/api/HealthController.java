package com.github.krystianmuchla.home.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping(value = "/api/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public HealthResponse getHealth() {
        return new HealthResponse();
    }

    public record HealthResponse() {
    }
}
