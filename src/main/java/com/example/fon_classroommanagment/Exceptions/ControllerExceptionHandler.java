package com.example.fon_classroommanagment.Exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ClassroomExistsException.class)
    public  ResponseEntity<String> HandleClassroomDoesNotExist(ClassroomExistsException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> HandleMethodArgumentsNotValid(ConstraintViolationException exception){
        return ResponseEntity.badRequest().body
                ( exception.getConstraintViolations().toArray()[0].toString());

    }    @ExceptionHandler(AppointmentDoesNotExistsException.class)
    public ResponseEntity<String> HandleAppointmentDoesNotExists(AppointmentDoesNotExistsException exception){
        return ResponseEntity.badRequest().body
                ( exception.getMessage());

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public  ResponseEntity<String> HandleEntityNotFoundException(EntityNotFoundException exception){
        return ResponseEntity.badRequest().body
                ( exception.getMessage());

    }
    @ExceptionHandler(ReservationExistsException.class)
    public  ResponseEntity<String> HandleReservationExistsException(ReservationExistsException exception){
        return ResponseEntity.badRequest().body
                ( exception.getMessage());

    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public  ResponseEntity<String> HandleUserNotFoundExistsException(UsernameNotFoundException exception){
        return ResponseEntity.badRequest().body
                ( exception.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> HandleAppointmentDoesNotExists(MethodArgumentNotValidException exception){
        return ResponseEntity.badRequest().body
                ( exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage());

    }

    @ExceptionHandler(UserExistsExcetion.class)
    public  ResponseEntity<String> HandleUserDoesNotExist(UserExistsExcetion exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(TokenNotFaundException.class)
    public  ResponseEntity<String> HandleTokenNotFaundExcecption(TokenNotFaundException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public  ResponseEntity<String> HandleTokenExpiredException(TokenExpiredException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(AppointmentsForUserException.class)
    public  ResponseEntity<String> HandleAppointmentsForUserException(AppointmentsForUserException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
