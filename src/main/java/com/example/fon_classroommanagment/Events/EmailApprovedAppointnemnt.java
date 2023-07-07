package com.example.fon_classroommanagment.Events;

import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class EmailApprovedAppointnemnt extends ApplicationEvent {
    @Getter
   private final Appointment appointment;
    public EmailApprovedAppointnemnt(Appointment source) {
        super(source);
        appointment=source;
    }
}
