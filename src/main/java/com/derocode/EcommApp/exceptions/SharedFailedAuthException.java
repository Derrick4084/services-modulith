package com.derocode.EcommApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SharedFailedAuthException extends RuntimeException {
    public SharedFailedAuthException(String message) {
        super(message);
    }
}
