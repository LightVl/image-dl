package com.example.imagedl;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;


@RestController
@Validated
public class ImageController {

    @Value("${jsonconfiguration.jsonpath}")
    private String jsonpathconf;
    @Autowired
    XmlRiverClient XMLClient;

    @GetMapping(value = "${jsonconfiguration.jsonpath}", produces = "application/json")
    public String getImage(@RequestParam(value = "name") @Size(min=1, max=40) String name, @RequestParam @Min(1) @Max(20) Integer qty) throws ParserConfigurationException, IOException, SAXException {
        String xmlString = XMLClient.getImages(name);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlString));
        Document doc = builder.parse(is);

        NodeList hiList = doc.getElementsByTagName("imgurl");
        String readyStr = "[\n";
        for (Integer i = 1; i <= qty; i++) {
            Node child = hiList.item(i);
            String contents = child.getTextContent();
            readyStr = readyStr + "{ \"id\": "+ i + ",\"url\": \"" + contents + "\"},\n";
        }
        readyStr = readyStr + "]";
        return readyStr;


    }
}