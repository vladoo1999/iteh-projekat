package com.example.fon_classroommanagment.Models.Appointment;

import com.example.fon_classroommanagment.Models.TypeClass;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.fon_classroommanagment.Configuration.Constants.APPOINTMENT_STATUS_TABLE_NAME;


/**
 * Klasa predstavlja status termina koji moze imati vrednosti accepted,declined,pending
 * @version 1.0
 *
 */
@Entity
@Table(name = APPOINTMENT_STATUS_TABLE_NAME)
@NoArgsConstructor
public class AppointmentStatus extends TypeClass {
    /**
     * Parametarski konstruktor postavlja id i name na unete vrednosti
     * @param id id statusa
     * @param name naziv statusa
     */
    public AppointmentStatus(Long id, String name) {
        super(id, name);
    }

    /**
     * Konstruktor koji postavlja id na uneti status
     * @param status status termina
     */
    public AppointmentStatus(Long status) {
        setId(status);
    }
}
