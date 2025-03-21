package com.example.imagedl.controller;

import com.example.imagedl.model.ImageLink;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@FeignClient(value = "xmlriver", url = "${xmlconfiguration.xmlurl}", configuration = XmlRiverClient.Configuration.class)
public interface XmlRiverClient {
    @GetMapping(value = "${xmlconfiguration.xmlpath}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    List<ImageLink> getImages(@PathVariable String name);

    class Configuration {
        @Bean
        public Decoder feignDecoder() {
      return (response, type) -> {
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
          builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
          throw new IllegalArgumentException("configuration failed " + e);
        }
        InputSource is = new InputSource(new StringReader(bodyStr));
        Document doc = null;
        try {
          doc = builder.parse(is);
        } catch (SAXException e) {
          throw new IllegalArgumentException("parsing failed " + e);
        }
        List<ImageLink> images = new ArrayList<>();
        NodeList hiList = doc.getElementsByTagName("imgurl");
        for (int i = 0; i < hiList.getLength(); i++) {
          Node child = hiList.item(i);
          String contents = child.getTextContent();
          images.add(new ImageLink(i+1, contents));
        }
        return images;
      };
        }
    }
}
