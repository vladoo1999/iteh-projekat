package com.example.fon_classroommanagment.Models.DTO.user;

import com.example.fon_classroommanagment.Models.User.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserRegistrationDTO {

        @NotNull(message = "email ne sme biti null")
        @Email(message = "email nije u dobrom formatu")
        private String email;
        @NotNull(message = "password ne sme biti null")
        @Size(min = 4,message = "Password mora imate bar 4 slova")
        private  String password;

//        @NotNull(message = "firstName ne sme biti null")
//        @Size(max = 20,message = "Ime moze imati najvise 20 karaktera")
//        private String firstName;
//
//        @NotNull(message = "Prezime ne sme biti null")
//        @Size(max = 20,message = "Prezime moze imate najvise 20 karaktera")
//        private String lastName;

        private String image;

        public Account createAccount() {
                return  new Account(email,image,"",password);
        }
}
