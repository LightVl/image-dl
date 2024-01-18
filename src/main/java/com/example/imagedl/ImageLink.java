package com.example.imagedl;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ImageLink {
    private Integer id;
    private String url;
    public ImageLink (Integer id, String url) {
        this.id = id;
        this.url = url;
    }
    public String getUrl() {
        return this.url;
    }
    public Integer getId() {
        return this.id;
    }
}
