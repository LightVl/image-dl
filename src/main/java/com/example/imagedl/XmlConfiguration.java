package com.example.imagedl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xmlconfiguration")
public class XmlConfiguration {
    public String xmlurl;
    public String xmlpath;
}
