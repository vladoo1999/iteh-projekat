package com.example.fon_classroommanagment.Models.DTO.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddEmployeeDTO {


    private String firstName;
    private String lastName;
    private String email;
    private Long department;
    private Long title;
    private Long type;

}

