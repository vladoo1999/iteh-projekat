package com.example.fon_classroommanagment.Events;

import com.example.fon_classroommanagment.Models.Email.Email;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


public class EmailEvent extends ApplicationEvent {

    @Getter
    private final Email email;

    public EmailEvent(Email source) {
        super(source);
        this.email=source;
    }
}
