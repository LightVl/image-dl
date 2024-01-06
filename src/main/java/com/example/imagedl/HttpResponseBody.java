package com.example.imagedl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class HttpResponseBody {
    private Date timestamp;
    private HttpStatus status;
    private String error;
    private String path;
}
