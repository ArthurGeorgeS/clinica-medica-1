package com.clinica.controller;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ErrorAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> conflito(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Registro duplicado ou em uso por outro cadastro.");
    }
}
