package com.example.imagedl;

import feign.Util;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "xmlriver", url = "https://xmlriver.com/", configuration = XMLRiverClient.Configuration.class)
public interface XMLRiverClient {

    @RequestMapping(method = RequestMethod.GET, value = "/search/xml?setab=images&user=11971&key=f64f40381be005af50a5abf88508e9a7c51274ed&query={name}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String getImages(@PathVariable String name);

    class Configuration {
        @Bean
        public Decoder feignDecoder() {
            return (response, type) -> {
                String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
                return bodyStr;
            };
        }
    }
}
