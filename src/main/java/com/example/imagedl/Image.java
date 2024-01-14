package com.example.imagedl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String message;

    public String getmessage() {
        return this.message;
    }
}
