package com.derocode.EcommApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SharedResourceExistsException extends RuntimeException {
    public SharedResourceExistsException(String message) {
        super(message);
    }
}
