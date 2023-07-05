package com.example.fon_classroommanagment.Models.DTO.appointment;

import com.example.fon_classroommanagment.Models.Appointment.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusAppointmentDTO {
    @NotNull(message = "Id ne sme biti prazan")
    private UUID id;
    @NotNull(message = "Status ne sme biti prazan")
    private AppointmentStatus status;

    public ChangeStatusAppointmentDTO(UUID id){
        this.id=id;
    }
}
