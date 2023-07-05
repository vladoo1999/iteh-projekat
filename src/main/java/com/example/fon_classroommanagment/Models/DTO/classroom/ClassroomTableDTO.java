package com.example.fon_classroommanagment.Models.DTO.classroom;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomTableDTO {

    private Long id;
    private String name;
    private String type;
    private int capacity;
}
