package com.tbb.demo.web;


import com.tbb.demo.exception.InvalidEntityException;
import com.tbb.demo.exception.NonexisitngEntityException;
import com.tbb.demo.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//TODO: change this parameter to routes or smth else
@ControllerAdvice(basePackageClasses = {com.tbb.demo.web.UsersController.class})
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExceptions(NonexisitngEntityException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleExceptions(InvalidEntityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}
