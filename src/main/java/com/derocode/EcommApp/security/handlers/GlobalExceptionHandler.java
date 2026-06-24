package com.derocode.EcommApp.security.handlers;

import com.derocode.EcommApp.exceptions.SharedFailedAuthException;
import com.derocode.EcommApp.exceptions.SharedResourceExistsException;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(@NonNull RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(@NonNull UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SharedResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(@NonNull SharedResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SharedResourceExistsException.class)
    public ResponseEntity<String> handleResourceExistsException(@NonNull SharedResourceExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(SharedFailedAuthException.class)
    public ResponseEntity<String> handleFailedAuthException(@NonNull SharedFailedAuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
