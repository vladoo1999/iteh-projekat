package com.example.fon_classroommanagment.Services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.fon_classroommanagment.Configuration.ExceptionMessages;
import com.example.fon_classroommanagment.Configuration.UserProfileDetails;
import com.example.fon_classroommanagment.Exceptions.AppointmentsForUserException;
import com.example.fon_classroommanagment.Exceptions.EmployeeExistException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import com.example.fon_classroommanagment.Models.DTO.*;
import com.example.fon_classroommanagment.Models.DTO.appointment.AppointmentRequestedUserDTO;
import com.example.fon_classroommanagment.Models.DTO.appointment.RequestedAppointmentsDTO;
import com.example.fon_classroommanagment.Models.DTO.user.*;
import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import com.example.fon_classroommanagment.Models.User.UserProfile;
import com.example.fon_classroommanagment.Repository.AppointmentRepository;
import com.example.fon_classroommanagment.Repository.EmployeeRepository;
import com.example.fon_classroommanagment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.fon_classroommanagment.Configuration.Constants.ADMIN_NAME_TYPE_ROLE;
import static com.example.fon_classroommanagment.Configuration.Constants.STATUS_PENDING;
import static com.example.fon_classroommanagment.Configuration.ExceptionMessages.EMPLOYEE_EXIST;

@Service
public class UserService implements UserDetailsService {

   private  final UserRepository userRepository;
@Autowired
    private EmployeeRepository employeeRepository;
   private final AppointmentRepository appointmentRepository;

    private final EmployeeService employeeService;

    public UserService(UserRepository userRepository, AppointmentRepository appointmentRepository, EmployeeService employeeService, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.employeeService = employeeService;
        this.encoder = encoder;

    }

    private final BCryptPasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user=findByEmail(username);

        if(user==null) throw  new UsernameNotFoundException(ExceptionMessages.USERNAME_NOT_FOUND);
        return new UserProfileDetails(user);
    }

    public UserProfile findByEmail(String email){
       return userRepository.findByEmail(email);
    }
    //save vraca uvek ne null ker radi update ili insert
    public boolean saveUser(UserProfile user){
        if(user==null) return false;
        userRepository.save(user);
        return true;

    }

    public UserProfile findById(UUID id) throws UserExistsExcetion {
        Optional<UserProfile> profile = userRepository.findById(id);
        if(profile.isEmpty()) throw new  UserExistsExcetion(ExceptionMessages.USER_EXISTS);
        return profile.get();
    }

    public void ChangePassword(ChangePasswordDTO password, String email) throws TokenExpiredException {
        UserProfile userProfile=findByEmail(email);
        if(userProfile==null) throw new TokenExpiredException(ExceptionMessages.TOKEN_EXPIRED);
        userRepository.updatePassword(userProfile.getId(),encoder.encode(password.getPassword()));

    }

    public void changeEmail(String email, ChangeEmailDTO dto) throws  TokenExpiredException {
        UserProfile userProfile=findByEmail(email);
        if(userProfile==null) throw new TokenExpiredException(ExceptionMessages.TOKEN_EXPIRED);
        userRepository.changeEmail(userProfile.getId(),dto.getEmail());
        employeeService.changeEmail(email,dto.getEmail());
    }

    public UserDetailsDTO getUserDetails(String email) throws  TokenExpiredException{
        UserProfile user=userRepository.findByEmail(email);
        if(user==null) throw new TokenExpiredException(ExceptionMessages.TOKEN_EXPIRED);
        Employee employee=user.getEmployee();
        UserDetailsDTO detailsDTO = new UserDetailsDTO(employee.getFirstName(), employee.getLastName(), employee.getType().getName(), employee.getImage());
        System.out.println(detailsDTO.getImage());
        return  detailsDTO;

    }

    public List<AppointmentsForUserDTO> getAppointmentsForUser(String email) throws AppointmentsForUserException {

        UserProfile user = userRepository.findByEmail(email);
        if(user==null) throw new TokenExpiredException(ExceptionMessages.TOKEN_EXPIRED);

        Employee employee = user.getEmployee();

        List<Appointment> appointments = appointmentRepository.findByEmployeeId(employee.getId());

       // if (appointments.isEmpty()) throw new AppointmentsForUserException("No reservations for this user");

        List<AppointmentsForUserDTO> appointmentsForUserDTOS = new ArrayList<>();
        for (Appointment appointment : appointments) {
            appointmentsForUserDTOS.add(new AppointmentsForUserDTO(
                    appointment.getId(),
                    appointment.getStatus().getId(),
                    appointment.getClassroom().getName(),
                    appointment.getName()
                  ,
                    appointment.getDate(),
                    appointment.getStart_timeInHours(),
                    appointment.getEnd_timeInHours()));

        }
        return appointmentsForUserDTOS;

    }

    public List<RequestedAppointmentsDTO> getRequestedAppointments() {
        List<RequestedAppointmentsDTO> requestedAppointmentsForUsers = appointmentRepository.getRequestedAppointmentsForUsers(STATUS_PENDING);

        if(requestedAppointmentsForUsers.size()==1 && requestedAppointmentsForUsers.get(0).getId()== null) return  List.of();
        return  requestedAppointmentsForUsers;
    }

    public List<EmployeeType> getAllEmpoyeeTypes(){
        return userRepository.getAlllEmployeeTypes();
    }
    public List<EducationTitle> getAllEducationTitles(){
        return userRepository.getAllEducationTitles();
    }
    public List<EmployeeDepartment> getAllEmployeeDepartments(){
        return userRepository.getAllEmployeeDepartments();
    }

    public Boolean isUserAdmin(String name) {

        return userRepository.findByEmail(name).getRole().getName().equals(ADMIN_NAME_TYPE_ROLE);
    }

    public List<AppointmentRequestedUserDTO> getAppointmentsPendingForUser(Long id) {
        return appointmentRepository.findByeAndEmployeeIdAndStatus(id,STATUS_PENDING);
    }
    public List<EmployeeAdminCardDTO> getEmployeesPermissions(String name) {
        return userRepository.getEmployeesPermissions(name);
    }
    public void updateRole(UpdateRoleDTO dto) {
        userRepository.updateUserRole(dto.getId_user(),dto.getId_role());
    }


    public List<AllEmployeesDTO> getAllEmployees() {
       return  userRepository.getAllEmployees();
    }

    public void addEmployee(AddEmployeeDTO dto) throws EmployeeExistException {
        if(employeeRepository.findByEmail(dto.getEmail()) != null) throw new EmployeeExistException(EMPLOYEE_EXIST);

        Employee employee = new Employee(dto.getFirstName(),dto.getLastName(),new EmployeeDepartment(dto.getDepartment()),new EducationTitle(dto.getTitle()),new EmployeeType(dto.getType()),dto.getEmail(),null);
        employeeRepository.save(employee);
    }
}
