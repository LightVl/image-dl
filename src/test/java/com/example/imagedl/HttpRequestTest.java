package com.example.imagedl;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/image",
                String.class)).contains("400");
    }
    @Test
    void success() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/image?name=tratata&qty=10",
                String.class)).contains("tratata");
    }
    @Test
    void blankNameCheck() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/image?name=",
                String.class)).contains("400");
    }
    @Test
    void lengthNameCheck() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/image?name=yfudjgyfudjgyfudjgyfudjgyfudjgyfudjgyfudjgyfudjgyfudjg&qty=10",
                String.class)).contains("400");
    }
    @Test
    void qtyCheckMin() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/image?name=gyfudjgyfudjgyfudjgyfudjgyfudjgyfudjg&qty=0",
                String.class)).contains("400");
    }
    @Test
    void qtyCheckMax() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/image?name=gyfudjgyfudjgyfudjgyfudjgyfudjgyfudjg&qty=50",
                String.class)).contains("400");
    }
}
