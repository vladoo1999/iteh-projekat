package com.example.fon_classroommanagment.Models.DTO.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAppointmentDTO {

    @NotNull(message = "Id ne sme biti prazan")
    private UUID id;
}
