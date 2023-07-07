package com.example.fon_classroommanagment.Repository;

import com.example.fon_classroommanagment.Models.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
}
