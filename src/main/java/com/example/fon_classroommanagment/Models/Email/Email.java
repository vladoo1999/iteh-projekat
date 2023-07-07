package com.example.fon_classroommanagment.Models.Email;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Email {

    private String to;
    private String from;
    private String content;
    private String template;
    private String subject;
    private Map<String,Object> variables;

}
