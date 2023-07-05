package com.example.fon_classroommanagment.Models.DTO.classroom;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomNamesDTO {

    @NotNull(message = "id ne sme biti prazan")
    private Long id;
    @NotNull(message = "Naziv ne sme biti prazan")
    @NotEmpty(message = "Naziv mora sadrzati neke karaktere")
    private String name;
}
