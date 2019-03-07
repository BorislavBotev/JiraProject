package com.example.demo.configs;



import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exceptions.UserException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler({NoSuchElementException.class})
    public  ResponseEntity<String> handleException(NoSuchElementException e) {
        System.out.println("fhfghfghfgh");
        return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.UNAUTHORIZED);
    }
	@ExceptionHandler({UserException.class})
    public  ResponseEntity<String> handleUserException(UserException e) {
        System.out.println("asddddddddd");
        return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.UNAUTHORIZED);
    }
}
