package com.example.imagedl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleRequestValidationException(Exception ex, HttpServletRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        //сделать вместо мапы класс и поприравнивать всё что ниже в конструкторе
        //погуглить респонсбади
        responseBody.put("timestamp", new Date());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", ex.getMessage());
        responseBody.put("path", request.getServletPath());
        //return new ErrorResponseBody(new Date(), HttpStatus.BAD_REQUEST, ex.getMessage(), request.getServletPath());
        return responseBody;
    }
}
