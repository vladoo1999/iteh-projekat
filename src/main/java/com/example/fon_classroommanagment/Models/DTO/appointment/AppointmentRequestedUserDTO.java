package com.example.fon_classroommanagment.Models.DTO.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestedUserDTO {

    @NotNull(message = "Id ne sme biti prazno")
    private UUID id;
    @NotNull(message = "Ime ucionice ne sme biti prazno")
    private String classroomName;
    @NotNull(message = "title ucionice ne sme biti prazno")
    private String title;

    @NotNull(message = "date ucionice ne sme biti prazno")
    private Date date;

    @Positive(message = "Pocetno vreme mora biti pozitivno")
    private int startTime;



    @Positive(message = "Krajnje vreme mora biti pozitivno")
    private int endTime;
}
