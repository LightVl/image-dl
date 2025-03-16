package com.example.imagedl.controller;

import com.example.imagedl.model.ImageLink;
import com.example.imagedl.model.Log;
import com.example.imagedl.repository.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class ImageController {

    @Getter
    @Setter
    private static Integer quantity;
    private final LogService logService;
    private final XmlRiverClient xmlClient;

    @GetMapping(value = "${jsonconfiguration.jsonpath}", produces = "application/json")
    public List<ImageLink> getImage(@RequestParam(value = "name") @Size(min=1, max=40) String name, @RequestParam @Min(1) @Max(20) Integer qty) throws IOException, InterruptedException {
        setQuantity(qty);
        String output = jsonSerializer(xmlClient.getImages(name));
        new Thread(() -> logService.add(new Log(name, qty, output))).start();
        return xmlClient.getImages(name);
    }
    public static String jsonSerializer (List<ImageLink> images) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(images);
    }
}