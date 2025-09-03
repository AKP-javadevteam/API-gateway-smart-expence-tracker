package com.smartexpense.gateway.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Gateway filter that forwards the userId (from JWT "sub" claim)
 * as an HTTP header "X-User-Id" to downstream services.
 *
 * No manual JSON parsing
 * Relies on Spring Security’s verified Jwt object
 */
@Component
public class AddUserHeaderFilter {

    @Override
    public <GatewayFilter> GatewayFilter apply(Object config) {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();

                    if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
                        String userId = jwt.getSubject(); // "sub" claim in JWT
                        return chain.filter(
                                exchange.mutate()
                                        .request(r -> r.headers(headers -> headers.add("X-User-Id", userId)))
                                        .build()
                        );
                    }

                    // If no auth or no JWT, just continue
                    return chain.filter(exchange);
                });
    }
}
