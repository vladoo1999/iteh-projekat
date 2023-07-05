package com.example.fon_classroommanagment.Models.DTO.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailDTO {



    @NotNull(message = "email ne sme biti null")
    @Email(message = "email nije u dobrom formatu")
    private String email;
}
