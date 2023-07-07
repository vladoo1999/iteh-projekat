package com.example.fon_classroommanagment.Repository;

import com.example.fon_classroommanagment.Models.User.Account;
import com.example.fon_classroommanagment.Models.User.ValidationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenValidationAccountRepository extends JpaRepository<ValidationToken,String > {
    ValidationToken findByRegisterDTO(Account account);
}
