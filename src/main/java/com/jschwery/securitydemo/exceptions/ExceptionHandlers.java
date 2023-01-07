package com.jschwery.securitydemo.exceptions;

import com.jschwery.securitydemo.entities.UserRegistrationForm;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(UserException.class)
    public String handleUserExistsAlready(UserException e, HttpServletResponse response){
        switch (e.getMessage()){

        }
        return "registration";
    }
}
