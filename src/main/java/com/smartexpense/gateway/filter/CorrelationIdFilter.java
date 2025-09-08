package com.smartexpense.gateway.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.UUID;

/**
 * Ensures every request has a correlation id for tracing across services.
 * Header used: X-Correlation-Id
 */
@Configuration
public class CorrelationIdFilter {

    public static final String CORRELATION_ID = "X-Correlation-Id";

    @Bean
    public <GlobalFilter> GlobalFilter correlationIdGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String cid = request.getHeaders().getFirst(CORRELATION_ID);
            if (cid == null || cid.isBlank()) {
                cid = UUID.randomUUID().toString();
                exchange = exchange.mutate()
                        .request(request.mutate().header(CORRELATION_ID, cid).build())
                        .build();
            }
            MDC.put(CORRELATION_ID, cid);
            String finalCid = cid;
            return chain.filter(exchange)
                    .doFinally(sig -> MDC.remove(CORRELATION_ID));
        };
    }
}
