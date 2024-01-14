package com.example.imagedl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
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
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Validated
public class ImageController {

    private static final String template = "Need %s pictures about %s!";
    @Autowired
    XMLRiverClient XMLClient;

    @GetMapping(value = "/image", produces = "application/json")
    public String getImage(@RequestParam(value = "name") @Size(min=1, max=40) String name, @RequestParam @Min(1) @Max(20) Integer qty) throws ParserConfigurationException, IOException, SAXException {
        //public String getImage(@RequestParam(value = "name") @Size(min=1, max=40) String name, @RequestParam @Min(1) @Max(20) Integer qty) {
        //return String.format(template, qty, name);
        //}
//        HttpResponse response = XMLClient.getImages();
//        HttpEntity r_entity = response.getEntity();
//        String xmlString = EntityUtils.toString(r_entity);
//        Map<String,Object> Context = new HashMap<>();
//        Context.put("servdep", new ServiceDependency());
//        Context.put("myserv", new MyService());
//        ((MyService)Context.get("myserv")).setDependency((ServiceDependency)Context.get("servdep"));

//        MyService tratata = new MyService();
//        tratata.setDependency(new ServiceDependency());
//        return XMLClient.getImages();
        //return String.format(template, qty, name);



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