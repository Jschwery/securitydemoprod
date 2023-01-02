package com.jschwery.securitydemo.exceptions;

import com.jschwery.securitydemo.entities.UserRegistrationForm;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(UserException.class)
    public String handleUserExistsAlready(UserException e, Model model){
        model.addAttribute("registrationForm", new UserRegistrationForm());
        model.addAttribute("registerError", true);
        model.addAttribute("errorMessage", e.getMessage());
        return "registration";
    }
}
