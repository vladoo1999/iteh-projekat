package com.example.fon_classroommanagment.Listener;

import com.example.fon_classroommanagment.Events.EmailApprovedAppointnemnt;
import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import com.example.fon_classroommanagment.Models.Email.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

import java.util.HashMap;
import java.util.Objects;

import static com.example.fon_classroommanagment.Configuration.Constants.*;

@Component
public class AppointmentApprovedEventListner implements ApplicationListener<EmailApprovedAppointnemnt> {
//    @Autowired
//    private EmailSender mailSender;
    @Override
    public void onApplicationEvent(EmailApprovedAppointnemnt event) {
        Appointment appointment=event.getAppointment();
        Email email=new Email(appointment.getEmployee().getEmail(),
                EMAIL_HOST_SENDER,
                appointment.getStatus().getName().equals(APPOINTMENT_APPROVED) ? "Appointment Approved":"Appointment Declined",
                EMAIL_APPOINTMENT_APPROVED_TEMPLATE,
                appointment.getStatus().getName().equals(APPOINTMENT_APPROVED) ? "Appointment Approved":"Appointment Declined",

                new HashMap<>()
                {{
                    put("nameEmployee", appointment.getEmployee().getFirstName());
                    put("nameClassroom",appointment.getClassroom().getName());
                    put("datum",appointment.getDate());
                            put("vreme",appointment.getStart_timeInHours()+"h "+"-"+appointment.getEnd_timeInHours()+"h");
                    put("tip",appointment.getType().getName());
                    put("status",(Objects.equals(appointment.getStatus().getName(), APPOINTMENT_APPROVED))?"dozvoljen":"odbijen");

                }});

//        try {
//            //mailSender.sendHtmlMessage(email);
//        } catch (MessagingException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
