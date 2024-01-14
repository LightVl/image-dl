package com.example.imagedl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.http.HttpResponse;
import java.util.List;

@FeignClient(value = "xmlriver", url = "https://xmlriver.com/", configuration = XMLRiverClient.Configuration.class)
//@FeignClient(value = "xmlriver", url = "https://jsonplaceholder.typicode.com")
//decode404 = true,
public interface XMLRiverClient {

    @RequestMapping(method = RequestMethod.GET, value = "/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query={name}", produces = MediaType.APPLICATION_XML_VALUE)
    //@RequestMapping(method = RequestMethod.GET, value = "/todos")
    @ResponseBody
    public String getImages(@PathVariable String name);

    class Configuration {
        @Bean
        public Decoder feignDecoder() {
            return (response, type) -> {
                String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
//                System.out.println("BODYSTRING=");
//                System.out.println(bodyStr);
//                System.out.println("BODYSTRING ended");
//                JavaType javaType = TypeFactory.defaultInstance().constructType(type);
//                XmlMapper xmlMapper = new XmlMapper();
//                System.out.println("xmlMapper=");
//                System.out.println(xmlMapper);
//                return (String)xmlMapper.readValue(bodyStr, javaType);
                return bodyStr;
            };
        }
    }
}
