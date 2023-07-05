package com.example.fon_classroommanagment.Models.Classroom;

import com.example.fon_classroommanagment.Models.TypeClass;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.fon_classroommanagment.Configuration.Constants.CLASSROOM_TYPE_TABLE_NAME;

/**
 * Klasa predstavlja tip ucionice koji moze imati vrednosti RC,amfiteatar,citaonica...
 * @version 1.0
 *
 */
@NoArgsConstructor
@Entity
@Table(name = CLASSROOM_TYPE_TABLE_NAME)
public class ClassroomType  extends TypeClass {
    /**
     * Parametarski konstruktor postavlja id i name na unete vrednosti
     * @param id id tipa ucionice
     * @param name naziv tipa
     */
    public ClassroomType(Long id, String name) {
        super(id, name);
    }
}
