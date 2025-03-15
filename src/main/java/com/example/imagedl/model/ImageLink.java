package com.example.imagedl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageLink {
    private Integer id;
    private String url;
    public ImageLink (Integer id, String url) {
        this.id = id;
        this.url = url;
    }
}
