package com.example.fon_classroommanagment.Models.DTO.appointment;

import com.example.fon_classroommanagment.Models.Appointment.AppointmentStatus;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AppointmentDetailsDTO {

    private UUID id;

    private String name;
    private  String decription;

    private String reason;

    private int number_of_attendies;

    private int Start_timeInHours;

    private int End_timeInHours;


private String classroomName;
    private Long classroomId;
    private AppointmentType type;
    private Date date;
}
