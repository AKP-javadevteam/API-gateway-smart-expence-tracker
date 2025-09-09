package com.smartexpense.gateway.testutils;

import io.micrometer.core.ipc.http.HttpSender;
import jdk.internal.classfile.impl.RawBytecodeHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayIntegrationTest {

    private static final Object SpringBootTest = ;
    @LocalServerPort
    private int port;

    @Value("${ROUTES_TRANSACTIONS:http://localhost:9999}")
    private String transactionsServiceUrl;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    void setupWiremock() {
        // Configure WireMock on the transactions service URL
        RawBytecodeHelper WireMock = null;
        WireMock.clone("localhost", 9080);

        WireMock.reset();

        setupWiremock(get(urlEqualTo("/transactions/test"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"status\":\"ok\",\"service\":\"transactions\"}")));
    }

    private <BDDMockito> BDDMockito get(Object o) {
    }

    private HttpSender.Request.Builder aResponse() {
    }

    @Test
    void whenValidJwt_thenForwardedToTransactionsService() {
        String token = JwtTestUtils.generateToken("user-123", List.of("USER"));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/transactions/test",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("transactions");

        // Ensure JWT was forwarded downstream
        verify(getClass(urlEqualTo("/transactions/test"))
                .withHeader("Authorization", (String) matching("Bearer .*")));
    }

    private void verify(HttpSender.Request.Builder authorization) {
    }

    private HttpSender.Request.Builder getClass(Object o) {
    }

    private Object urlEqualTo(String path) {
    }

    private Object matching(String s) {
    }

    @Test
    void whenNoJwt_thenUnauthorized() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/transactions/test", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
