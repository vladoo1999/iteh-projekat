package com.example.fon_classroommanagment.Controllers;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.fon_classroommanagment.Events.AccountRegistrationRequestEvent;
import com.example.fon_classroommanagment.Exceptions.TokenNotFaundException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Models.DTO.user.UserRegistrationDTO;
import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import com.example.fon_classroommanagment.Models.User.Account;
import com.example.fon_classroommanagment.Models.User.ValidationToken;
import com.example.fon_classroommanagment.Services.AccountService;
import com.example.fon_classroommanagment.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.example.fon_classroommanagment.Configuration.Routes.REGISTER;
import static com.example.fon_classroommanagment.Configuration.Routes.REGISTER_CONFIRM;

/**
 * AuthenticationController obradjuje zahteve vezane za autentifikaciju
 * @version 1.0
 */
@RestController
@Validated
public class AuthenticationController {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private EmployeeService employeeService;



    /**
     * zavisnost accountService u kojoj se nalazi logika vezana za korisnicki nalog
     */
@Autowired
private AccountService accountService;


    /**
     * Metoda za registrovanje korisnika
     * @param registerDTO dto koji sadrzi email,password,firstName,lastName
     * @throws UserExistsExcetion ako je korisnik vec registrovan
     */
    @PostMapping (REGISTER)
    public void registerAccount(
            @RequestBody @Valid UserRegistrationDTO registerDTO) throws UserExistsExcetion {
       Account account=registerDTO.createAccount();
       System.out.println(account.getImage().length());
        ValidationToken token=  accountService.createValidationToken(account);
        account.setToken(token.getToken());

        Employee emp = employeeService.findByEmail(registerDTO.getEmail());
        account.setFirstName(emp.getFirstName());
        account.setLastName(emp.getLastName());

        publisher.publishEvent(new AccountRegistrationRequestEvent(account));

    }

    /**
     * Metoda za potvrdjivanje naloga
     * @param token token za identifikaciju korisnika
     * @throws TokenNotFaundException ako token nije pronadjen
     * @throws TokenExpiredException ako je token istekao
     */
    @GetMapping(REGISTER_CONFIRM)
    public void registerAccountConfirmed(@PathVariable("token")  String token) throws TokenNotFaundException, TokenExpiredException {
        accountService.ConfirmAccount(token);
    }





}
