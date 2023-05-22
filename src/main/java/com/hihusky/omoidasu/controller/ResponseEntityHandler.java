package com.hihusky.omoidasu.controller;


import com.hihusky.omoidasu.exception.DictEntryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
@Slf4j
public class ResponseEntityHandler {
    @ExceptionHandler(DictEntryNotFoundException.class)
    public String handleEntryNotFoundException(DictEntryNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

}
