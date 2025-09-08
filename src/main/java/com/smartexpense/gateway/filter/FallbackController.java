package com.smartexpense.gateway.filter;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Circuit-breaker fallback. Routes can forward here on failure:
 *  filters:
 *    - name: CircuitBreaker
 *      args:
 *        name: reportsCB
 *        fallbackUri: forward:/fallback/reports
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, Object> reportsFallback() {
        return Map.of(
                "message", "Reports service is temporarily unavailable. Please try again.",
                "service", "reports",
                "status", 503
        );
    }
}

