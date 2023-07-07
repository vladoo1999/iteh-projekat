package com.example.fon_classroommanagment.Models.Emplayee;

import com.example.fon_classroommanagment.Models.TypeClass;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.fon_classroommanagment.Configuration.Constants.EDUCATION_TITLE_TABLE_NAME;


/**
 * Klasa predstavlja obrazovanje zaposlenog koje moze imati vrednosti osnovne studije,master,doktorske studije...
 * @version 1.0
 *
 */
@Entity
@Table(name = EDUCATION_TITLE_TABLE_NAME)
@NoArgsConstructor
public class EducationTitle extends TypeClass {
    /**
     * Parametarski konstruktor postavlja id i name na unete vrednosti
     * @param id
     * @param name
     */
    public EducationTitle(Long id, String name) {
        super(id, name);
    }
    public EducationTitle(Long id) {
        setId(id);
    }
}
