package com.example.imagedl;

import ch.qos.logback.classic.LoggerContext;
import com.example.imagedl.controller.ImageController;
import com.example.imagedl.controller.XmlRiverClient;
import com.example.imagedl.model.ImageLink;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@TestPropertySource("/test.properties")
@WireMockTest
public class WireMockTestAnnotationTest extends AbstractTestClass{

    private static WireMockServer wireMockServer;
    @Autowired private XmlRiverClient xmlClient;
    @Autowired private TestRestTemplate restTemplate;
    @LocalServerPort private int port;

    @Value("${xmlconfiguration.xmlurl}")
    private String url;

    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }
    @BeforeEach
    void initResponse () throws IOException {
        File file = new File("src/main/resources/textXml.xml");
        String responseBody = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String apiUrl = "/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query=batman";
        System.out.println(url);
        System.out.println(responseBody);
        configureFor("localhost", 8089);
        //Define stub
        stubFor(get(apiUrl).willReturn(ok(responseBody)));
    }
    @AfterAll
    static void end() {
        wireMockServer.stop();
    }
    @Test
    @SneakyThrows
    void simpleStubTesting(WireMockRuntimeInfo wmRuntimeInfo) throws JsonProcessingException {
        List<ImageLink> fullList2 = xmlClient.getImages("batman");
        String result = ImageController.jsonSerializer(fullList2);
        System.out.println(result);
        assertThat(result.contains("[{\"id\":1"));
    }
    @Test
    @SneakyThrows
    void success() {
        final ResponseEntity<String> response =
                restTemplate.getForEntity(
                        String.format("http://localhost:%d/image?name=batman&qty=2", port), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    @SneakyThrows
    void checkOKBody() {
        final ResponseEntity<String> response =
                restTemplate.getForEntity(
                        String.format("http://localhost:%d/image?name=batman&qty=2", port), String.class);
        assertThat(response.getBody()).contains("[{\"id\":1,\"url\":");
    }
}