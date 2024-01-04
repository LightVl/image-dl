package com.example.imagedl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private static final String template = "Need %s pictures about %s!";
    //private final AtomicLong counter = new AtomicLong();

    @GetMapping("/image")
    public Image image(@RequestParam(value = "name", defaultValue = "World") String name, @RequestParam(value = "qty", defaultValue = "10") String qty) {
        return new Image(String.format(template, qty, name));
    }
}