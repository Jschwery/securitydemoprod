package com.jschwery.securitydemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserException> userCreationFailed(UserException ue){
        return new ResponseEntity<>(new UserException(ue.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
