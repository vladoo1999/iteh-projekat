package com.example.fon_classroommanagment.Controllers;

import com.example.fon_classroommanagment.Models.Appointment.AppointmentType;
import com.example.fon_classroommanagment.Models.Classroom.ClassroomType;
import com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomNamesDTO;
import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import com.example.fon_classroommanagment.Models.User.UserRole;
import com.example.fon_classroommanagment.Services.ClassroomService;
import com.example.fon_classroommanagment.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.fon_classroommanagment.Configuration.Routes.*;

/**
 * CommonDataController obradjuje zahteve vezane za opste podatke
 * @version 1.0
 */
@RestController
@RequestMapping(COMMON_PREFIX)
public class CommonDataController {

    /**
     * zavisnost UserService u kojoj se nalazi logika vezana za usere
     */
    @Autowired private UserService userService;

    /**
     * zavisnost ClassroomService u kojoj se nalazi logika vezana za ucionice
     */
    @Autowired
    private ClassroomService classrromService;

    /**
     * Metoda koja vraca listu tipova zaposlenih
     * @return HTTP odgovor koji u telu sadrzi listu informacija o tipu zaposlenog
     */
    @GetMapping(COMMON_ALL_EMPLOYEE_TYPES)
    public ResponseEntity<List<EmployeeType>> getAllEmployeeTypes(){
        return ResponseEntity.ok(userService.getAllEmpoyeeTypes());
    }
    /**
     * Metoda koja vraca listu obrazovanja
     * @return HTTP odgovor koji u telu sadrzi listu obrazovanja
     */
    @GetMapping(COMMON_ALL_EDUCATION_TITLES)
    public ResponseEntity<List<EducationTitle>> getAllEducationTitles(){
        return ResponseEntity.ok(userService.getAllEducationTitles());
    }
    /**
     * Metoda koja vraca listu odeljenja
     * @return HTTP odgovor koji u telu sadrzi listu odeljenja
     */
    @GetMapping(COMMON_ALL_EMPLOYEE_DEPARTMENTS)
    public ResponseEntity<List<EmployeeDepartment>> getAllEmployeeDepartments(){
        return ResponseEntity.ok(userService.getAllEmployeeDepartments());
    }
    /**
     * Metoda koja vraca listu tipa ucionica
     * @return HTTP odgovor koji u telu sadrzi listu ucionica
     */

    @GetMapping(COMMON_ALL_CLASSROOM_TYPES)
    public ResponseEntity<List<ClassroomType>> getAllClassroomTypes(){
        return ResponseEntity.ok(classrromService.getAllClassroomTypes());
    }
    /**
     * Metoda koja vraca listu tipova termina
     * @return HTTP odgovor koji u telu sadrzi listu tipova termina
     */
    @GetMapping(COMMON_ALL_APPOINTMENT_TYPES)
    public ResponseEntity<List<AppointmentType>> getAllAppointmentTypes(){
        return ResponseEntity.ok(classrromService.getAllAppointmentTypes());
    }
    /**
     * Metoda koja vraca listu dozvola usera
     * @return HTTP odgovor koji u telu sadrzi listu dozvola
     */
    @GetMapping(COMMON_USER_ROLES)
    public ResponseEntity<List<UserRole>> getAllUserRoles(){
        return ResponseEntity.ok(classrromService.getAllUserRoles());
    }

    @GetMapping(COMMON_ALL_CLASSROOM_NAMES)
    public ResponseEntity<List<ClassroomNamesDTO>> getClassroomNames(){
        return ResponseEntity.ok(classrromService.getAllClassroomNames());
    }
}
