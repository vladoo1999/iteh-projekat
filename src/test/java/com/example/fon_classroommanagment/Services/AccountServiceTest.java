package com.example.fon_classroommanagment.Services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.fon_classroommanagment.Exceptions.TokenNotFaundException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import com.example.fon_classroommanagment.Models.User.Account;
import com.example.fon_classroommanagment.Models.User.ValidationToken;
import com.example.fon_classroommanagment.Repository.AccountRepository;
import com.example.fon_classroommanagment.Repository.TokenValidationAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.example.fon_classroommanagment.Configuration.Constants.EXPIRATION_TIME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Test
    void createValidationToken_Pass() throws UserExistsExcetion {
        Account account=new Account("test@gmail.com","1234");
        ValidationToken toReturn=new ValidationToken("123456789",account);
        AccountService mock = Mockito.mock(AccountService.class);
        Mockito.when(mock.createValidationToken(account)).thenReturn(toReturn);
        assertEquals(toReturn,mock.createValidationToken(account));

    }
    @Test
    void createValidationToken_Fail() throws UserExistsExcetion {
        Account account=new Account("test@gmail.com","1234");
        Account account2=new Account("threertg@gmail.com","1234");
        ValidationToken toReturn=new ValidationToken("123456789",account);
        AccountService mock = Mockito.mock(AccountService.class);
        Mockito.when(mock.createValidationToken(account)).thenReturn(toReturn);
        assertNotEquals(toReturn,mock.createValidationToken(account2));

    }

    @Test
    void confirmAccount_Pass() throws TokenNotFaundException {
        String token="123456789";
        Employee employee=new Employee(1L,"test","test",new EmployeeDepartment(1L,"test"),new EducationTitle(1L,"test"),new EmployeeType(1L,"test"),"test@gmail.com",null);
        ValidationToken VToken=new ValidationToken(token,new Account("test@gmail.com","1234","test",null,token,"1234"));
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        TokenValidationAccountRepository mockTokenValidationRepo = Mockito.mock(TokenValidationAccountRepository.class);
        UserService mockUserService = Mockito.mock(UserService.class);
        EmployeeService mockEmployeeService = Mockito.mock(EmployeeService.class);
        BCryptPasswordEncoder mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        AccountService service=new AccountService(mockTokenValidationRepo,mockRepo,mockUserService,mockEmployeeService,mockEncoder);
        when(mockEmployeeService.findByEmail("test@gmail.com")).thenReturn(employee);
        when(mockTokenValidationRepo.findById(token)).thenReturn(Optional.of(VToken));
       when(mockRepo.findByEmail(VToken.getRegisterDTO().getEmail())).thenReturn(VToken.getRegisterDTO());
        service.ConfirmAccount(token);
        verify(mockTokenValidationRepo,times(1)).findById(token);
        verify(mockRepo,times(1)).findByEmail(VToken.getRegisterDTO().getEmail());

    }  @Test
    void confirmAccount_TokenNotFaundException() throws TokenNotFaundException {
        String token="123456789";
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        TokenValidationAccountRepository mockTokenValidationRepo = Mockito.mock(TokenValidationAccountRepository.class);
        UserService mockUserService = Mockito.mock(UserService.class);
        EmployeeService mockEmployeeService = Mockito.mock(EmployeeService.class);
        BCryptPasswordEncoder mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        AccountService service=new AccountService(mockTokenValidationRepo,mockRepo,mockUserService,mockEmployeeService,mockEncoder);
        when(mockTokenValidationRepo.findById(token)).thenReturn(Optional.empty());
        assertThrows(TokenNotFaundException.class,()->{service.ConfirmAccount(token);});
        //Mockito.verify(mockRepo, Mockito.times(1)).findById(token);
        //mock.ConfirmAccount(token);
    }

    @Test
    void confirmAccount_TokenExpiredException() throws TokenNotFaundException {
        String token = "123456789";
        ValidationToken VToken = new ValidationToken(token,  new Date( Calendar.getInstance().getTimeInMillis() + (EXPIRATION_TIME+1000)),new Account("test@gmail.com", "1234", "test",null, token, "1234"));
        VToken.setExpirationDate(new Date());
        AccountRepository mockRepo = Mockito.mock(AccountRepository.class);
        TokenValidationAccountRepository mockTokenValidationRepo = Mockito.mock(TokenValidationAccountRepository.class);
        UserService mockUserService = Mockito.mock(UserService.class);
        EmployeeService mockEmployeeService = Mockito.mock(EmployeeService.class);
        BCryptPasswordEncoder mockEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        AccountService service = new AccountService(mockTokenValidationRepo, mockRepo, mockUserService, mockEmployeeService, mockEncoder);

        when(mockTokenValidationRepo.findById(token)).thenReturn(Optional.of(VToken));
        assertThrows(TokenExpiredException.class,()->{service.ConfirmAccount(token);});

    }

}