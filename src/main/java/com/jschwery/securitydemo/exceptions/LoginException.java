package com.jschwery.securitydemo.exceptions;

import org.springframework.http.HttpStatus;

public class LoginException extends RuntimeException{

    public LoginException(){
        super();
    }

    private HttpStatus status = null;

    private Object data = null;


    public LoginException(
            String message
    ) {
        super(message);
    }

    public LoginException(
            HttpStatus status,
            String message
    ) {
        this(message);
        this.status = status;
    }

    public LoginException(
            HttpStatus status,
            String message,
            Object data) {
        this(status, message);
        this.data = data;
    }
}
