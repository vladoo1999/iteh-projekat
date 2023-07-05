package com.example.fon_classroommanagment.Models.Classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Positive;

import static com.example.fon_classroommanagment.Configuration.Constants.CLASSROOM_TABLE_NAME;


/**
 * Klasa predstavlja ucionicu u kojoj korisnik rezervise termin.
 * @version 1.0
 *
 */
@Entity
@Table(name = CLASSROOM_TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Classroom {
    /**
     * Identifikator ucionice tipa Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Naziv ucionice tipa String.
     */
    @Column(columnDefinition = "VARCHAR(45)",nullable = false)
    private String name;
    /**
     * Broj mesta za sedenje kao int.
     */
    @Column(columnDefinition = "INT(11) UNSIGNED",nullable = false)
    private int number_of_seats;
    /**
     * Broj mesta za sedenje koja imaju racunar kao int.
     */
    @Column(columnDefinition = "INT(11) UNSIGNED",nullable = false)
    private int number_of_computers;
    /**
     * Da li ucionica poseduje klimu kao boolean.
     */
    @Column(columnDefinition = "TINYINT(1)",nullable = false)

    private boolean aircondition;
    /**
     * Da li ucionica poseduje projektor kao boolean.
     */
    @Column(columnDefinition = "TINYINT(1)",nullable = false)
    private boolean projector;
    /**
     * Povrsina ucionice kao int.
     */
    @Positive(message = "Povrsina mora biti pozitivan broj")
    private int area;
    /**
     * Sprat na kojem se ucionica nalazi kao int.
     */
    @Positive(message = "Sprat mora biti pozitivan broj")
    private int floor;
    /**
     * Broj tabli u ucionici kao int.
     */
    @Positive(message = "Broj tabli mora biti pozitivan broj")
    private int num_tables;

    /**
     * Tip ucionice kao ClassroomType.
     */
    @ManyToOne( optional = false)
    private ClassroomType type;

/**
     * Konstruktor koji postavlja id na unetu vrednost
     * @param classroomId
     */
    public Classroom(long classroomId) {
        setId(classroomId);
    }
}
