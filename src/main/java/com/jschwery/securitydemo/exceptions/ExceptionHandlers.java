package com.jschwery.securitydemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserException.class)
    public String userCreationFailed(UserException ue, Model model){
        model.addAttribute("error", true);
        model.addAttribute("exceptionMessage", ue.getMessage());
        return "login";
    }

}
