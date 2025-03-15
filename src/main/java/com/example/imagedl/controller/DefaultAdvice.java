package com.example.imagedl.controller;

import com.example.imagedl.model.ErrorResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseBody handleRequestValidationException(Exception ex, HttpServletRequest request) {
        return new ErrorResponseBody(new Date(), HttpStatus.BAD_REQUEST, ex.getMessage(), request.getServletPath());
    }
}
