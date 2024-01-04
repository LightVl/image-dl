package com.example.imagedl;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private static final String template = "Hello, %s!";
    //private final AtomicLong counter = new AtomicLong();

    @GetMapping("/image")
    public Image image(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Image(String.format(template, name));
    }
}