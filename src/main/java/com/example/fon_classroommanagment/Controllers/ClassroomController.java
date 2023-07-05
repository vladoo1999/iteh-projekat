package com.example.fon_classroommanagment.Controllers;

import com.example.fon_classroommanagment.Exceptions.ClassroomExistsException;
import com.example.fon_classroommanagment.Models.DTO.*;
import com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomCardDTO;
import com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomDetailsDTO;
import com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomTableDTO;
import com.example.fon_classroommanagment.Models.DTO.classroom.FilterDTO;
import com.example.fon_classroommanagment.Services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.example.fon_classroommanagment.Configuration.Routes.*;

/**
 * ClassroomController obradjuje zahteve vezane za ucionicu
 * @version 1.0
 */
@RestController
@RequestMapping(CLASSROOM_PREFIX)
@Validated
public class ClassroomController {
    /**
     * zavisnost ClassroomService u kojoj se nalazi logika vezana za ucionicu
     */
    @Autowired
    private ClassroomService service;



    /**
     * Metoda koja vraca listu ucionice na osnovu odredjenih kriterijuma pretrage
     * @param page strana sa koje hocemo informacije o ucionicama
     * @param filterDTO sadrzi min_capacity,max_capacity,types,aircondition,projector
     * @return HTTP odgovor koji u telu sadrzi listu informacija o ucionici
     */
    @PostMapping(CLASSROOM_PAGING)
    public ResponseEntity<List<ClassroomCardDTO>> getClassrooms(
            @PathVariable("page")  @Valid @Positive(message = "Page mora biti pozitivan broj") int page,
             @RequestBody FilterDTO filterDTO

    ){
        return  ResponseEntity.ok(service.getAllClassrooms(page-1,filterDTO));


    }

    /**
     * Metoda za pretragu ucionica
     * @param page strana sa koje hocemo ucionicu
     * @param name ime ucionice
     * @return HTTP odgovor koji u telu sadrzi listu informacija o ucionici
     */
    @GetMapping(CLASSROOM_SEARCH)
    public ResponseEntity<List<ClassroomCardDTO>> searchClassroom(@PathVariable("page")  @Positive(message = "Page mora biti pozitivan broj") int page,@RequestParam("name") String name) {
       return ResponseEntity.ok(service.searchClassroom(page,name));


    }

    /**
     * Metoda za dobijanje detalja ucionice
     * @param classroomId identifikator ucionice
     * @return HTTP odgovor koji u telu sadrzi listu detalja o ucionici
     * @throws ClassroomExistsException ako ucionica ne postoji
     */
    @GetMapping(CLASSROOM_DETAILS)
    public ResponseEntity<ClassroomDetailsDTO> classroomDetails(
            @RequestParam("id")
            @Positive(message = "Id ucionice more biti pozitivan")
            @NotNull(message = "Id ucionice ne sme biti prazan") Long classroomId) throws ClassroomExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(service.classroomDetails(classroomId));
    }



//    @PostMapping(CLASSROOM_APPOITMENTS)
//    public ResponseEntity<List<GetForDateAppointmentDTO>> getAppointmentsForDateClassroom(@RequestBody @Valid RequestIsClassroomAvailableForDateDTO requestAppointmetDateDTO) throws ClassroomExistsException {
//
//        return  ResponseEntity.ok(service.getForDateClassroom(requestAppointmetDateDTO));
//    }


    @GetMapping(CLASSROOM_PARTIAL_INFO)
    public ResponseEntity<List<ClassroomChipDTO>> getClassroomsAsChip(@RequestParam("name") String name){
        return ResponseEntity.ok(service.getClassroomsAsChips(name));
    }
    @GetMapping(CLASSROOM_PAGING_PARTIAL_INFO)
    public ResponseEntity<List<ClassroomChipDTO>> getClassroomsAsChip( @PathVariable("page") @Positive(message = "Page mora biti pozitivan broj")  int page ){
        return ResponseEntity.ok(service.getAllClassroomsAsChips(page));
    }

    @GetMapping(CLASSROOM_TABLE)
    public ResponseEntity<List<ClassroomTableDTO>> getClassroomTable(){
        return ResponseEntity.ok(service.getClassroomTable());
    }






}
