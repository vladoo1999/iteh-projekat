package com.example.fon_classroommanagment.Models.Appointment;

import com.example.fon_classroommanagment.Anotations.CheckValues;
import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static com.example.fon_classroommanagment.Configuration.Constants.APPOINTMENT_TABLE_NAME;


/**
 * Klasa predstavlja termin koji korisnik rezervise.
 * @version 1.0
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = APPOINTMENT_TABLE_NAME)
public class Appointment {


    /**
     * Identifikator termina tipa UUID
     */
    @Id
    @Type(type = "uuid-char")
    private UUID id;

    /**
     * Zaposleni koji rezervise termin
     */
    @ManyToOne( optional = false)
    private Employee employee;


    /**
     * Ucionica za koju se rezervise termin
     */
    @ManyToOne( optional = false)
    private Classroom classroom;


    /**
     * Naziv termina kao String.
     */
    @Column(columnDefinition = "VARCHAR(45)",nullable = false)
    private String name;

    /**
     * Datum za koji se vezuje termin kao Date.
     */
    @Column(columnDefinition = "DATE",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;


    /**
     * Opis u okviru termina kao String
     */
    @Column(columnDefinition = "VARCHAR(45)",nullable = false)
    private  String decription;
    /**
     * Razlog rezervacije termina kao String.
     */
    @Column(columnDefinition = "VARCHAR(45)",nullable = false)
    private String reason;
    /**
     * Broj prisutnih kao int
     */
    @Column(columnDefinition = "INT(11) UNSIGNED",nullable = false)
    private int number_of_attendies;
    /**
     * Vreme pocetka termina kao int
     */
    @Column(columnDefinition = "INT(11) UNSIGNED",nullable = false)
    private int Start_timeInHours;
    /**
     * Vreme zavrsetka termina kao int
     */
    @Column(columnDefinition = "INT(11) UNSIGNED",nullable = false)
    private int End_timeInHours;

    /**
     * Status termina kao AppointmentStatus
     */
    @ManyToOne( optional = false)
    @JoinColumn
    private AppointmentStatus status;
    /**
     * Tip termina kao AppointmentType
     */
    @ManyToOne( optional = false)
    @JoinColumn
    private AppointmentType type;


}
