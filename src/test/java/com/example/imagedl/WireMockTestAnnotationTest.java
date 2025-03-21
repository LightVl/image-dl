package com.example.imagedl;

import com.example.imagedl.controller.ImageController;
import com.example.imagedl.controller.XmlRiverClient;
import com.example.imagedl.model.ImageLink;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
@TestPropertySource("/test.properties")
@WireMockTest
public class WireMockTestAnnotationTest extends AbstractTestClass{

    @Autowired private XmlRiverClient xmlClient;

    @Value("${xmlconfiguration.xmlurl}")
    private String url;

    @Test
    void simpleStubTesting(WireMockRuntimeInfo wmRuntimeInfo) throws JsonProcessingException {
        WireMockServer wireMockServer = new WireMockServer(8089);
        wireMockServer.start();

    String responseBody =
        "<yandexsearch version=\"1.0\">\n"
            + "<response date=\"20250318T143621\">\n"
            + "<found priority=\"all\">100</found>\n"
            + "<suggestedsearches>\n"
            + "<item>\n"
            + "<name>Обои</name>\n"
            + "</item>\n"
            + "<item>\n"
            + "<name>Лего</name>\n"
            + "</item>\n"
            + "<item>\n"
            + "<name>Комикс</name>\n"
            + "</item>\n"
            + "<item>\n"
            + "<name>Рабочий стол</name>\n"
            + "</item>\n"
            + "<item>\n"
            + "<name>Нарисовать</name>\n"
            + "</item>\n"
            + "</suggestedsearches>\n"
            + "<results>\n"
            + "<grouping>\n"
            + "<page first=\"1\" last=\"100\"/>\n"
            + "<group id=\"1\">\n"
            + "<doccount>1</doccount>\n"
            + "<doc>\n"
            + "<url>https://ru.wikipedia.org/wiki/Бэтмен</url>\n"
            + "<imgurl>https://upload.wikimedia.org/wikipedia/ru/a/a2/Batman_Jim_Lee.jpg</imgurl>\n"
            + "<title>Бэтмен — Википедия</title>\n"
            + "<displaylink>Википедия</displaylink>\n"
            + "<originalwidth>640</originalwidth>\n"
            + "<originalheight>1024</originalheight>\n"
            + "<passages>\n"
            + "<passage/>\n"
            + "</passages>\n"
            + "</doc>\n"
            + "</group>\n"
            + "<group id=\"2\">\n"
            + "<doccount>1</doccount>\n"
            + "<doc>\n"
            + "<url>https://dc-universe.fandom.com/ru/wiki/Бэтмен</url>\n"
            + "<imgurl>https://static.wikia.nocookie.net/dc-universe/images/0/00/Batman-inc-1-variant.jpg/revision/latest?cb\\u003d20150910051002\\u0026path-prefix\\u003dru</imgurl>\n"
            + "<title>Бэтмен | DC Universe вики | Fandom</title>\n"
            + "<displaylink>DC Universe Wiki - Fandom</displaylink>\n"
            + "<originalwidth>900</originalwidth>\n"
            + "<originalheight>1368</originalheight>\n"
            + "<passages>\n"
            + "<passage/>\n"
            + "</passages>\n"
            + "</doc>\n"
            + "</group>\n"
            + "</grouping>\n"
            + "</results>\n"
            + "</response>\n"
            + "</yandexsearch>\n";

        String apiUrl = "/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query=batman";
        System.out.println(url);
        configureFor("localhost", 8089);
        //Define stub
        stubFor(get(apiUrl).willReturn(ok(responseBody)));
        List<ImageLink> fullList2 = xmlClient.getImages("batman");
        String result = ImageController.jsonSerializer(fullList2);
        System.out.println(result);
        assertThat(result.contains("[{\"id\":1"));
        wireMockServer.stop();
    }
}