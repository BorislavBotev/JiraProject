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
    public  ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler({Exception.class})
    public  ResponseEntity<String> handleAllException(UserException e) {
        return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }
}