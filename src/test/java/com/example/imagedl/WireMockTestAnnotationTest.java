package com.example.imagedl;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest
public class WireMockTestAnnotationTest {

    @Test
    void simpleStubTesting(WireMockRuntimeInfo wmRuntimeInfo) {
        String responseBody = "Hello World !!";
        String apiUrl = "/xmlriver.com/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query=batman";

        //Define stub
        stubFor(get(apiUrl).willReturn(ok(responseBody)));

        //Hit API and check response
        String apiResponse = getContent(wmRuntimeInfo.getHttpBaseUrl() + apiUrl);
        assertEquals(apiResponse, responseBody);

        //Verify API is hit
        verify(getRequestedFor(urlEqualTo(apiUrl)));
    }

    private String getContent(String url) {

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        return testRestTemplate.getForObject(url, String.class);
    }
}