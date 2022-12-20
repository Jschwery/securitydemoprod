package com.jschwery.securitydemo.exceptions;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    public UserException(){
        super();
   }
    private HttpStatus status = null;

    private Object data = null;


    public UserException(
            String message
    ) {
        super(message);
    }

    public UserException(
            HttpStatus status,
            String message
    ) {
        this(message);
        this.status = status;
    }

    public UserException(
            HttpStatus status,
            String message,
            Object data) {
        this(status, message);
        this.data = data;
    }
}
