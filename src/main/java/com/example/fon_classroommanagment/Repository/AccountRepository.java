package com.example.fon_classroommanagment.Repository;

import com.example.fon_classroommanagment.Models.User.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account,String> {

    Account findByEmail(String email);
}
