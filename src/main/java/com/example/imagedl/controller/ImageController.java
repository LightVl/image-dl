package com.example.imagedl.controller;

import com.example.imagedl.model.ImageLink;
import com.example.imagedl.model.Log;
import com.example.imagedl.repository.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
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
    private final LogService logService;
    private final XmlRiverClient xmlClient;

    @GetMapping(value = "${jsonconfiguration.jsonpath}", produces = "application/json")
    public List<ImageLink> getImage(@RequestParam(value = "name") @Size(min=1, max=40) String name, @RequestParam @Min(1) @Max(20) Integer qty) throws IOException{
        List<ImageLink> fullList = xmlClient.getImages(name);
        //fullList.subList(qty,fullList.size()).clear();
        List<List<ImageLink>> lists = Lists.partition(fullList,qty);
        List<ImageLink> readyList = lists.getFirst();
        String output = jsonSerializer(readyList);
        new Thread(() -> logService.add(new Log(name, qty, output))).start();
        return readyList;
    }
    public static String jsonSerializer (List<ImageLink> images) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(images);
    }
}