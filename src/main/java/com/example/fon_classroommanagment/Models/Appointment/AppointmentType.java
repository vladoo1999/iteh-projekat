package com.example.fon_classroommanagment.Models.Appointment;

import com.example.fon_classroommanagment.Models.TypeClass;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.fon_classroommanagment.Configuration.Constants.APPOINTMENT_TYPE_TABLE_NAME;


/**
 * Klasa predstavlja tip termina koji moze imati vrednosti predavanje,vezbe,kolokvijum,ispit..
 * @version 1.0
 *
 */
@Entity
@Table(name = APPOINTMENT_TYPE_TABLE_NAME)
@NoArgsConstructor
public class AppointmentType extends TypeClass {
    /**
     * Parametarski konstruktor postavlja id i name na unete vrednosti
     * @param id id tipa
     * @param name naziv tipa
     */
    public AppointmentType(Long id, String name) {
        super(id, name);
    }
    /**
     * Konstruktor koji postavlja id na uneti tip
     * @param type
     */
    public AppointmentType(Long type) {
        setId(type);
    }
}
