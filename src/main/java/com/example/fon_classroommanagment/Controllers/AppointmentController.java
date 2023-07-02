package com.example.fon_classroommanagment.Controllers;

import com.example.fon_classroommanagment.Events.AccountRegistrationRequestEvent;
import com.example.fon_classroommanagment.Events.EmailApprovedAppointnemnt;
import com.example.fon_classroommanagment.Exceptions.AppointmentDoesNotExistsException;
import com.example.fon_classroommanagment.Exceptions.ReservationExistsException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Listener.AppointmentApprovedEventListner;
import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentStatus;
import com.example.fon_classroommanagment.Models.DTO.appointment.*;
import com.example.fon_classroommanagment.Models.DTO.classroom.RequestIsClassroomAvailableForDateDTO;
import com.example.fon_classroommanagment.Services.AppointmentService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.fon_classroommanagment.Configuration.Constants.*;
import static com.example.fon_classroommanagment.Configuration.ExceptionMessages.APPOINTMENT_RESERVED;
import static com.example.fon_classroommanagment.Configuration.Routes.*;

/**
 * AppointmentController obradjuje zahteve vezane za termin
 * @version 1.0
 */
@RestController()
@RequestMapping(APPOINTMENT_PREFIX)
@Validated
public class AppointmentController {
    /**
     * zavisnost appointmentService u kojoj se nalazi logika vezana za termin
     */
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Metoda koja na osnovu id-ja termina i mejla zaposlenog brise termin
     * @param dto id termina za brisanje
     * @param authentication podaci korisnika koji brise termin
     * @throws UserExistsExcetion ako korisnik ne postoji
     */
        @DeleteMapping(APPOINTMENT_DELETE)
        public void DeleteAppointment(@RequestParam("id") @Valid UUID dto,Authentication authentication,Authentication auth) throws UserExistsExcetion {

            appointmentService.DeleteAppointment(dto.toString(),authentication.getName(),auth.getAuthorities().toArray()[0].toString());

        }

    /**
     * Metoda koja vraca sve termine
     * @return HTTP odgovor koji u telu sadrzi listu termina
     */
    @GetMapping(APPOINTMENTS)
        public ResponseEntity<List<Appointment>> getAll(){
            return ResponseEntity.ok(appointmentService.getAll());
        }




    /**
     * Metoda za potvrdu termina
     * @param appointmentId id termina kojeg hocemo da potvrdimo
     * @throws AppointmentDoesNotExistsException ako termin sa zadatim identifikatorom ne postoji
     */
    @PostMapping(APPOINTMENT_CONFIRM)
        public void ConfirmAppointment(@RequestBody AppointmentAcceptDTO appointmentId) throws AppointmentDoesNotExistsException {
              appointmentService.ConfirmAppointment(UUID.fromString(appointmentId.getId()));
        UUID uuid =  UUID.fromString(appointmentId.getId());

        Appointment appointment = appointmentService.FindById(uuid).get();
        appointment.setStatus(new AppointmentStatus(1L,APPOINTMENT_APPROVED));
        publisher.publishEvent(new EmailApprovedAppointnemnt(appointment));
        }

    /**
     * Metoda za odbijanje termina
     * @param appointmentId id termina kojeg hocemo da odbijemo
     * @throws AppointmentDoesNotExistsException ako termin sa zadatim identifikatorom ne postoji
     */
    @PostMapping(APPOINTMENT_DECLINE)
        public void DeclineAppointment(@RequestBody AppointmentDeclineDTO appointmentId) throws AppointmentDoesNotExistsException {
              appointmentService.DeclineAppointment(UUID.fromString(appointmentId.getId()));
         UUID uuid =  UUID.fromString(appointmentId.getId());

        Appointment appointment = appointmentService.FindById(uuid).get();
        appointment.setStatus(new AppointmentStatus(2L,APPOINTMENT_DECLINED));
            publisher.publishEvent(new EmailApprovedAppointnemnt(appointment));
        }

    /**
     * Metoda za potvrdu liste termina
     * @param dto lista identifikatora termina za odobravanje
     * @throws AppointmentDoesNotExistsException ako termin sa zadatim identifikatorom ne postoji
     */
    @PostMapping(APPOINTMENT_CONFIRM_ALL)
        public void ConfirmAppointment(@RequestBody List<String> dto) throws AppointmentDoesNotExistsException {
            appointmentService.ConfirmAllAppointments(dto.stream().map(UUID::fromString).collect(Collectors.toList()));
        }

    /**
     * Metoda za rezervisanje liste termina
     * @param dto lista objekata rezervacija
     * @param authentication podaci o korisniku koji rezervise, ako je administrator termin se odmah odobrava
     * @throws ReservationExistsException ako vec postoji termin sa istim vremenom i datumom
     */
        @PostMapping(value = APPOINTMENT_RESERVE)
        public ResponseEntity<?> Reserve(@RequestBody List<ReserveDTO> dto, Authentication authentication) {
            try {
                appointmentService.ReserveAppointment(dto,authentication.getAuthorities().toArray()[0].toString());
                return new ResponseEntity<>("Appointment successfully created", HttpStatus.OK);
            } catch (ReservationExistsException e) {
                return new ResponseEntity<>(APPOINTMENT_RESERVED,HttpStatus.BAD_REQUEST);
            }

        }

    /**
     * Metoda za pretragu termina
     * @param dto dto koji sadrzi date,classroomId i start_timeInHours
     * @return HTTP odgovor koji u telu sadrzi listu termina
     * @throws ReservationExistsException ako termin nije pronadjen za uneti datum i vreme
     */
        @GetMapping(APPOINTMENT_SEARCH)
        public ResponseEntity<List<Appointment>> searchReservation(@RequestBody  @Valid SearchAppointmentDTO dto) throws ReservationExistsException {
            return ResponseEntity.status(HttpStatus.OK).body(appointmentService.searchReservation(dto));
        }

        @GetMapping(APPOINTMENT_DETAILS)
        public ResponseEntity<AppointmentDetailsDTO> getAppointmentDetails(@RequestParam("id")  String  id)  {


            return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAppointmentDetails(UUID.fromString(id)));
        }

    /**
     * Metoda koja vraca termine na osnovu datuma
     * @param date datum kada je termin rezervisan
     * @return HTTP odgovor koji u telu sadrzi listu termina za trazeni datum
     */

    @PostMapping(APPOINTMENT_DATE)
        public ResponseEntity<List<GetForDateAppointmentDTO>> appointmentAvailability(@PathVariable @Valid @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
            return  ResponseEntity.ok(appointmentService.getForDate(date));
        }

    /**
     * Metoda koja vraca termine na osnovu datuma i ucionica u kojoj su rezervisani
     * @param requestAppointmetDateClassroomDTO dto koji sadrzi date i classroomId
     * @return HTTP odgovor koji u telu sadrzi listu termina za trazeni datum i ucionicu
     */
    @PostMapping(APPOINTMENT_CLASSROOM)
        public ResponseEntity<List<GetForDateAppointmentDTO>> classroomAvailability(@RequestBody @Valid RequestAppointmetDateForClassroomDTO requestAppointmetDateClassroomDTO){
            return  ResponseEntity.ok(appointmentService.getForDateAndClassroom(requestAppointmetDateClassroomDTO));
        }

    /**
     * Metoda koja proverava da li se termin moze rezervisati
     * @param dto dto koji sadrzi date,classroomId,start_timeInHours,end_timeInHours
     * @return HTTP odgovor koji u telu sadrzi informaciju o tome da li je termin vec zauzet
     */
    @PostMapping(APPOINTMENT_AVAILABILITY)
        public ResponseEntity<Boolean> getIsClassroomAvailableForDate(@RequestBody @Valid RequestIsClassroomAvailableForDateDTO dto ){
          return  ResponseEntity.ok(appointmentService.IsClassroomAvailableAtDate(dto));
        }

    /**
     * Metoda koja azurira termin
     * @param dto sadrzi podatke termina koji se azuriraju
     * @throws ReservationExistsException ako je termin vec rezervisan
     */
    @PatchMapping(APPOINTMENT_UPDATE)
        public ResponseEntity<?> updateReservation(@RequestBody @Valid UpdateAppointmentDTO dto) throws ReservationExistsException {
        try {
            appointmentService.updateReservation(dto);
            return new ResponseEntity<>("Appointment successfully edited", HttpStatus.OK);
        } catch (ReservationExistsException e) {
            return new ResponseEntity<>(APPOINTMENT_RESERVED,HttpStatus.BAD_REQUEST);
        }

        }

}
