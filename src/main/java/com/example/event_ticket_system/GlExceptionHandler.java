package com.example.event_ticket_system;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("timestamp:", LocalDate.now());
        map.put("message", "Unexpected error. Please try again later.");
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(map,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
