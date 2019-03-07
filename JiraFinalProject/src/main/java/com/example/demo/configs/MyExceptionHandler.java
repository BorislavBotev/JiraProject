package com.example.demo.configs;



import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler({NoSuchElementException.class})
    public  ResponseEntity<String> handleException(NoSuchElementException e) {
//        response.setStatus(401);
//        response.getOutputStream().print(e.getMessage());
        System.out.println("fhfghfghfgh");
        return new ResponseEntity<>("Invalid username or password",new HttpHeaders(),HttpStatus.UNAUTHORIZED);
    }
}
