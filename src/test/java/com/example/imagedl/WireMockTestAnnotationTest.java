package com.example.imagedl;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration
@TestPropertySource("/test.properties")
@WireMockTest
public class WireMockTestAnnotationTest {

    @Value("${xmlconfiguration.xmlurl}")
    private String url="http://localhost:8089";

    @Test
    void simpleStubTesting(WireMockRuntimeInfo wmRuntimeInfo) {
        WireMockServer wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        String responseBody = "Hello World !!";
        String apiUrl = "/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query=batman";
        System.out.println(url);
        configureFor("localhost", 8089);
        //Define stub
        stubFor(get(apiUrl).willReturn(ok(responseBody)));

        //Hit API and check response
        String apiResponse = getContent(url+"/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query=batman");
    assertEquals("Hello World !!", apiResponse);

        //Verify API is hit
        verify(getRequestedFor(urlEqualTo(apiUrl)));
        wireMockServer.stop();
    }

    private String getContent(String url) {

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        return testRestTemplate.getForObject(url, String.class);
    }
}