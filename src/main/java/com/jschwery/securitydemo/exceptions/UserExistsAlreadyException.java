package com.jschwery.securitydemo.exceptions;

import org.springframework.http.HttpStatus;

public class UserExistsAlreadyException extends RuntimeException{
    public UserExistsAlreadyException(){
        super();
    }
    private HttpStatus status = null;

    private Object data = null;


    public UserExistsAlreadyException(
            String message
    ) {
        super(message);
    }

    public UserExistsAlreadyException(
            HttpStatus status,
            String message
    ) {
        this(message);
        this.status = status;
    }

    public UserExistsAlreadyException(
            HttpStatus status,
            String message,
            Object data) {
        this(status, message);
        this.data = data;
    }
}
