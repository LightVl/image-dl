package com.example.imagedl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;


@RestController
@Validated
public class ImageController {

    public static Integer quantity;
    @Autowired
    XmlRiverClient XMLClient;

    @GetMapping(value = "${jsonconfiguration.jsonpath}", produces = "application/json")
    public String getImage(@RequestParam(value = "name") @Size(min=1, max=40) String name, @RequestParam @Min(1) @Max(20) Integer qty) throws IOException {
        quantity = qty;
        return jsonSerializer(XMLClient.getImages(name));
    }
    public static String jsonSerializer (List<ImageLink> Images) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(Images);
    }
}