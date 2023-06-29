package com.example.fon_classroommanagment.Events;

import com.example.fon_classroommanagment.Models.User.Account;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


public class AccountRegistrationRequestEvent extends ApplicationEvent {

    @Getter
    private final Account dto;
    public AccountRegistrationRequestEvent(Account source) {
        super(source);
        this.dto=source;
    }
}
