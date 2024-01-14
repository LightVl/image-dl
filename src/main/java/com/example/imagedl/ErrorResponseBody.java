package com.example.imagedl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
public class ErrorResponseBody {
    private Date timestamp;
    private HttpStatus status;
    private String error;
    private String path;

    public ErrorResponseBody(Date date, HttpStatus httpStatus, String message, String servletPath) {
        this.timestamp = date;
        this.status = httpStatus;
        this.error = message;
        this.path = servletPath;
    }
}
