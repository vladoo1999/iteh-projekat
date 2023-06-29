package com.example.fon_classroommanagment.Listener;

import com.example.fon_classroommanagment.Events.AccountRegistrationRequestEvent;
import com.example.fon_classroommanagment.Models.Email.Email;

import com.example.fon_classroommanagment.Models.User.Account;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


import java.util.HashMap;

import static com.example.fon_classroommanagment.Configuration.Constants.EMAIL_HOST_SENDER;
import static com.example.fon_classroommanagment.Configuration.Constants.EMAIL_REGISTRATION_REQUEST_TEMPLATE;
import static com.example.fon_classroommanagment.Configuration.Routes.REGISTER;

@Component
public class AccountRegistrationRequestListner implements ApplicationListener<AccountRegistrationRequestEvent> {

//  @Autowired
//  private EmailSender mailSender;
    @Override
    public void onApplicationEvent(AccountRegistrationRequestEvent event) {
        Account dto=event.getDto();
        Email email=new Email(dto.getEmail(),EMAIL_HOST_SENDER,"test1",EMAIL_REGISTRATION_REQUEST_TEMPLATE,"Registration",
                new HashMap<>()
                {{
                    put("name", dto.getFirstName());
                    put("path",REGISTER+"/"+dto.getToken());

                }});



    }
}
