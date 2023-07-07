package com.example.fon_classroommanagment.Repository;

import com.example.fon_classroommanagment.Models.DTO.user.AllEmployeesDTO;
import com.example.fon_classroommanagment.Models.DTO.user.EmployeeAdminCardDTO;
import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import com.example.fon_classroommanagment.Models.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<UserProfile, UUID> {
    UserProfile findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update UserProfile u set u.password = :password where u.id = :id")
    void updatePassword(@Param(value = "id") UUID id, @Param(value = "password") String password);

    @Transactional
    @Modifying
    @Query("update UserProfile u set u.email=:email where u.id=:id")
    void changeEmail(@Param("id") UUID id, @Param("email") String email);

    @Query("select e from EmployeeType  e")
    List<EmployeeType> getAlllEmployeeTypes();

    @Query("select e from EducationTitle  e")
    List<EducationTitle> getAllEducationTitles();

    @Query("select e from EmployeeDepartment  e")
    List<EmployeeDepartment> getAllEmployeeDepartments();


    @Query("select  new com.example.fon_classroommanagment.Models.DTO.user.EmployeeAdminCardDTO(e.id,e.employee.firstName,e.employee.lastName,e.role.name,e.employee.image) from UserProfile  e where e.email<>:email")
    List<EmployeeAdminCardDTO> getEmployeesPermissions(@Param("email") String name);

    @Transactional
    @Modifying
    @Query("update UserProfile u set u.role.id=:user_role where u.id=:id")
    void updateUserRole(UUID id, Long user_role);

    @Query("select  new com.example.fon_classroommanagment.Models.DTO.user.AllEmployeesDTO(e.firstName,e.lastName,e.email,e.department.name,e.title.name,e.type.name) from Employee e")
    List<AllEmployeesDTO> getAllEmployees();
}
