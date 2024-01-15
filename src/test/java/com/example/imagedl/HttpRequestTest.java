package com.example.imagedl;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest extends AbstractTestClass {

    @LocalServerPort
    private int port;

    @Autowired
    private ImageController controller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @SneakyThrows
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
    @Test
    @SneakyThrows
    void greetingShouldReturnDefaultMessage() {
        final ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:%d/image", port), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    @SneakyThrows
    void success() {
        final ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:%d/image?name=tratata&qty=10", port), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    @SneakyThrows
    void badcheck1() {
        final ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:%d/image?name=", port), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    @SneakyThrows
    void badcheck2() {
        final ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:%d/image?name=witcherwitcherwitcherwitcherwitcherwitcherwitcherwitcherwitcherwitcher&qty=10", port), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    @SneakyThrows
    void badcheck3() {
        final ResponseEntity<Image> response = restTemplate.getForEntity(String.format("http://localhost:%d/image?name=witcher&qty=0", port), Image.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    @Test
    @SneakyThrows
    void badcheck4() {
        final ResponseEntity<Image> response = restTemplate.getForEntity(String.format("http://localhost:%d/image?name=witcher&qty=50", port), Image.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
