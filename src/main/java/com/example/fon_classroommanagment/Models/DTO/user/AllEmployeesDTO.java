package com.example.fon_classroommanagment.Models.DTO.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllEmployeesDTO {


    private String  firstName;
    private String  lastName;
    private String email;
    private String department;
    private String title;
    private String type;


}
