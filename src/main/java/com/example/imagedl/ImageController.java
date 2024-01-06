package com.example.imagedl;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
public class ImageController {

    private static final String template = "Need %s pictures about %s!";

    @GetMapping(value = "/image")
    public Image getImage(@RequestParam(value = "name") @Size(min=1, max=40) String name, @RequestParam @Min(1) @Max(20) Integer qty) {
        return new Image(String.format(template, qty, name));
    }
}