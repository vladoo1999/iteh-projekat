package com.example.fon_classroommanagment.Services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.fon_classroommanagment.Configuration.Constants;
import com.example.fon_classroommanagment.Configuration.ExceptionMessages;
import com.example.fon_classroommanagment.Exceptions.AppointmentsForUserException;
import com.example.fon_classroommanagment.Exceptions.ClassroomExistsException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentStatus;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentType;
import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.example.fon_classroommanagment.Models.Classroom.ClassroomType;
import com.example.fon_classroommanagment.Models.DTO.appointment.AppointmentRequestedUserDTO;
import com.example.fon_classroommanagment.Models.DTO.appointment.RequestedAppointmentsDTO;
import com.example.fon_classroommanagment.Models.DTO.user.ChangeEmailDTO;
import com.example.fon_classroommanagment.Models.DTO.user.ChangePasswordDTO;
import com.example.fon_classroommanagment.Models.DTO.user.EmployeeAdminCardDTO;
import com.example.fon_classroommanagment.Models.DTO.user.UpdateRoleDTO;
import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import com.example.fon_classroommanagment.Models.User.UserProfile;
import com.example.fon_classroommanagment.Models.User.UserRole;
import com.example.fon_classroommanagment.Repository.AppointmentRepository;
import com.example.fon_classroommanagment.Repository.EmployeeRepository;
import com.example.fon_classroommanagment.Repository.UserRepository;
import com.sun.mail.util.BEncoderStream;
import com.sun.mail.util.QEncoderStream;
import org.apache.tomcat.util.buf.UEncoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;


import static com.example.fon_classroommanagment.Configuration.Constants.ADMIN_NAME_TYPE_ROLE;
import static com.example.fon_classroommanagment.Configuration.Constants.STATUS_PENDING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private  static UserService service;

    private static UserRepository userRepository;


    private static AppointmentRepository appointmentRepository;

    private static EmployeeService employeeService;


    private  static BCryptPasswordEncoder encoder;




    @BeforeAll
    static void Start(){
       userRepository = mock(UserRepository.class);
        appointmentRepository = mock(AppointmentRepository.class);
        employeeService = mock(EmployeeService.class);
        encoder = mock(BCryptPasswordEncoder.class);
        service = new UserService(userRepository,appointmentRepository,employeeService,encoder);

    }




    @Test
    void loadUserByUsernameUsernameNotFound() {
        String username = "test@gmail.com";
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));

        when(userRepository.findByEmail(username)).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,()->service.loadUserByUsername(username));

    }


    @Test
    void saveUser() {
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));

        service.saveUser(userProfile);
        verify(userRepository,times(1)).save(userProfile);
    }

    @Test
    void findById() throws UserExistsExcetion {
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));
        Optional<UserProfile> optional = Optional.of(userProfile);

        when(userRepository.findById(userProfile.getId())).thenReturn(optional);
        service.findById(userProfile.getId());
        verify(userRepository,times(1)).findById(userProfile.getId());
    }


    @Test
    void findByIdUserDoesntExist() {
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));


        when(userRepository.findById(userProfile.getId())).thenReturn(Optional.empty());
        assertThrows(UserExistsExcetion.class,()->service.findById(userProfile.getId()));
    }

    @Test
    void changePassword() {
        userRepository = mock(UserRepository.class);
        appointmentRepository = mock(AppointmentRepository.class);
        employeeService = mock(EmployeeService.class);
        encoder = mock(BCryptPasswordEncoder.class);
        service = new UserService(userRepository,appointmentRepository,employeeService,encoder);
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));
        ChangePasswordDTO dto = new ChangePasswordDTO("12334",new Date());

        when(userRepository.findByEmail(userProfile.getEmail())).thenReturn(userProfile);
        service.findByEmail(userProfile.getEmail());
        verify(userRepository,times(1)).findByEmail(userProfile.getEmail());

        service.ChangePassword(dto, userProfile.getEmail());
        verify(userRepository,times(1)).updatePassword(userProfile.getId(),encoder.encode(dto.getPassword()));

    }

    @Test
    void changeEmail() {
        String email = "test@gmail.com";
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));
        ChangeEmailDTO dto = new ChangeEmailDTO("aa@gmail.com");
        when(userRepository.findByEmail(email)).thenReturn(userProfile);
        service.findByEmail(email);
        verify(userRepository,times(1)).findByEmail(email);

        service.changeEmail(email, dto);
        verify(userRepository,times(1)).changeEmail(userProfile.getId(),dto.getEmail());
        verify(employeeService,times(1)).changeEmail(email,dto.getEmail());
    }

    @Test
    void tokenExpired(){
        String email = "test@gmail.com";
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));
        ChangeEmailDTO dto = new ChangeEmailDTO("aa@gmail.com");
        when(userRepository.findByEmail(email)).thenReturn(null);
        assertThrows(TokenExpiredException.class,()->service.changeEmail(email,dto));

    }


    @Test
    void getUserDetails() {
        userRepository = mock(UserRepository.class);
        appointmentRepository = mock(AppointmentRepository.class);
        employeeService = mock(EmployeeService.class);
        encoder = mock(BCryptPasswordEncoder.class);
        service = new UserService(userRepository,appointmentRepository,employeeService,encoder);
        String email = "test@gmail.com";
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));

        when(userRepository.findByEmail(email)).thenReturn(userProfile);
        service.getUserDetails(email);
        verify(userRepository,times(1)).findByEmail(email);


    }

    @Test
    void getAppointmentsForUser() throws AppointmentsForUserException {
        userRepository = mock(UserRepository.class);
        appointmentRepository = mock(AppointmentRepository.class);
        employeeService = mock(EmployeeService.class);
        encoder = mock(BCryptPasswordEncoder.class);
        service = new UserService(userRepository,appointmentRepository,employeeService,encoder);
        String email = "test@gmail.com";
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"USER"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));
        Employee employee = userProfile.getEmployee();
        List<Appointment> appointments=new LinkedList<>(){
            {
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));

            }
        };


        when(userRepository.findByEmail(email)).thenReturn(userProfile);
        service.findByEmail(email);
        verify(userRepository,times(1)).findByEmail(email);

        when(appointmentRepository.findByEmployeeId(employee.getId())).thenReturn(appointments);
        service.getAppointmentsForUser(email);
        verify(appointmentRepository,times(1)).findByEmployeeId(employee.getId());

    }

    @Test
    void getRequestedAppointments() {
        List<RequestedAppointmentsDTO> appointments=new LinkedList<>(){
            {
                add(new RequestedAppointmentsDTO(1L,"predavanje",null,"aleksa","st",11L));
                add(new RequestedAppointmentsDTO(2L,"vezbe",null,"aleksa","st",11L));
            }
        };

        when(appointmentRepository.getRequestedAppointmentsForUsers(STATUS_PENDING)).thenReturn(appointments);
        service.getRequestedAppointments();
        verify(appointmentRepository,times(1)).getRequestedAppointmentsForUsers(STATUS_PENDING);
    }

    @Test
    void getAllEmpoyeeTypes() {
        List<EmployeeType> employeeTypes =new LinkedList<>(){
            {
                add(new EmployeeType(1L,"profesor"));
                add(new EmployeeType(2L,"asistent"));
                add(new EmployeeType(3L,"saradnik"));
            }
        };
        when(userRepository.getAlllEmployeeTypes()).thenReturn(employeeTypes);
        service.getAllEmpoyeeTypes();
        verify(userRepository,times(1)).getAlllEmployeeTypes();


    }

    @Test
    void getAllEducationTitles() {
        List<EducationTitle> educationTitles =new LinkedList<>(){
            {
                add(new EducationTitle(1L,"doktorat"));
                add(new EducationTitle(2L,"master"));

            }
        };
        when(userRepository.getAllEducationTitles()).thenReturn(educationTitles);
        service.getAllEducationTitles();
        verify(userRepository,times(1)).getAllEducationTitles();
    }

    @Test
    void getAllEmployeeDepartments() {
        List<EmployeeDepartment> employeeDepartments =new LinkedList<>(){
            {
                add(new EmployeeDepartment(1L,"IS"));
                add(new EmployeeDepartment(2L,"SI"));

            }
        };
        when(userRepository.getAllEmployeeDepartments()).thenReturn(employeeDepartments);
        service.getAllEmployeeDepartments();
        verify(userRepository,times(1)).getAllEmployeeDepartments();
    }

    @Test
    void isUserAdmin() {
        String email = "test@gmail.com";
        UserProfile userProfile = new UserProfile(UUID.randomUUID(),"test@gmail.com","1234", new UserRole(1L,"ADMIN"), new Employee(1L,"aleks","st",new EmployeeDepartment(1L,"TESt"),new EducationTitle(1L,"TEST"),new EmployeeType(1L,"TEST"),"aa@gmail.com",null));
        when(userRepository.findByEmail(email)).thenReturn(userProfile);
        assertEquals(userRepository.findByEmail(email).getRole().getName(),ADMIN_NAME_TYPE_ROLE);
    }

    @Test
    void getAppointmentsPendingForUser() {
        List<AppointmentRequestedUserDTO> dtos =new LinkedList<>(){
            {
                add(new AppointmentRequestedUserDTO(UUID.randomUUID(),"C201","test",new Date(),11,12));
                add(new AppointmentRequestedUserDTO(UUID.randomUUID(),"D201","test",new Date(),11,12));

            }
        };
        Long id = 1L;

        when(appointmentRepository.findByeAndEmployeeIdAndStatus(id,STATUS_PENDING)).thenReturn(dtos);
        service.getAppointmentsPendingForUser(id);
        verify(appointmentRepository,times(1)).findByeAndEmployeeIdAndStatus(id,STATUS_PENDING);
    }



    @Test
    void updateRole() {
        UpdateRoleDTO dto = new UpdateRoleDTO(UUID.randomUUID(),1L);

        service.updateRole(dto);
        verify(userRepository,times(1)).updateUserRole(dto.getId_user(), dto.getId_role());
    }
}