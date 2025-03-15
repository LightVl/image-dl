package com.example.imagedl;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Data
@Component
@ConfigurationProperties(prefix = "xmlconfiguration")
public class XmlConfiguration {
    private String xmlurl;
    private String xmlpath;
}
