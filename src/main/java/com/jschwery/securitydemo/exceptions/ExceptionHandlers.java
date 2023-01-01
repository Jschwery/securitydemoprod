package com.jschwery.securitydemo.exceptions;

import com.jschwery.securitydemo.entities.UserRegistrationForm;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserException.class)
    public String userCreationFailed(UserException ue, Model model){
        model.addAttribute("error", true);
        model.addAttribute("exceptionMessage", ue.getMessage());
        return "login";
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String handleUserCreationFailed(ConstraintViolationException cv, Model model){
        model.addAttribute("error", true);
        model.addAttribute("errorMessage", "User already exists!");

        return "registration";
    }
    @ExceptionHandler(UserExistsAlreadyException.class)
    public String handleUserExistsAlready(UserExistsAlreadyException e, Model model){
        UserRegistrationForm registrationForm = new UserRegistrationForm();
        model.addAttribute("registrationForm", registrationForm);
        model.addAttribute("registerError", true);
        model.addAttribute("errorMessage", e.getMessage());
        return "registration";
    }
}
