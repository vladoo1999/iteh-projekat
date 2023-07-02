package com.example.fon_classroommanagment.Services;

import com.example.fon_classroommanagment.Configuration.Constants;
import com.example.fon_classroommanagment.Exceptions.AppointmentDoesNotExistsException;
import com.example.fon_classroommanagment.Exceptions.ReservationExistsException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentStatus;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentType;
import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.example.fon_classroommanagment.Models.Classroom.ClassroomType;
import com.example.fon_classroommanagment.Models.DTO.appointment.*;
import com.example.fon_classroommanagment.Models.DTO.classroom.RequestIsClassroomAvailableForDateDTO;
import com.example.fon_classroommanagment.Models.Emplayee.EducationTitle;
import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeDepartment;
import com.example.fon_classroommanagment.Models.Emplayee.EmployeeType;
import com.example.fon_classroommanagment.Models.User.UserProfile;
import com.example.fon_classroommanagment.Models.User.UserRole;
import com.example.fon_classroommanagment.Repository.AppointmentRepository;
import com.example.fon_classroommanagment.Repository.EmployeeRepository;
import com.example.fon_classroommanagment.Repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.example.fon_classroommanagment.Configuration.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    private static AppointmentService service;
            static AppointmentRepository appointmentRepository;
        static EmployeeRepository employeeRepository;
        static UserRepository userRepository;

    @BeforeAll
    static void Start(){
         appointmentRepository=mock(AppointmentRepository.class);
         employeeRepository=mock(EmployeeRepository.class);
         userRepository=mock(UserRepository.class);
         service=new AppointmentService(appointmentRepository,employeeRepository,userRepository);
    }


    @Test
    void deleteAppointment() throws UserExistsExcetion {
        UUID id=UUID.randomUUID();
        String role = "ADMIN";
        String appointmentId=id.toString();
        String email="test@gmail.com";
        Employee toReturn=new Employee("test","test",new EmployeeDepartment(1L,"test"),new EducationTitle(1L,"test"),new EmployeeType(1L,"test"),"test@gmail.com",null);
        when(employeeRepository.findByEmail(email)).thenReturn(toReturn);
        service.DeleteAppointment(appointmentId,email,role);
        verify(appointmentRepository,times(1)).deleteByIdAndAndEmployee(UUID.fromString(appointmentId),toReturn);
    }
    @Test
    void deleteAppointment_UserExistsExcetion() throws UserExistsExcetion {
        UUID id=UUID.randomUUID();
        String role = "ADMIN";
        String appointmentId=id.toString();
        String email="test@gmail.com";
        Employee toReturn=new Employee("test","test",new EmployeeDepartment(1L,"test"),new EducationTitle(1L,"test"),new EmployeeType(1L,"test"),"test@gmaiol.com",null);

        when(employeeRepository.findByEmail(email)).thenReturn(null);
        assertThrows(UserExistsExcetion.class,()->service.DeleteAppointment(appointmentId,email,role));
    }

    @Test
    void getAll() {
        List<Appointment> lista=new LinkedList<>(){
            {add(new Appointment());
                add(new Appointment());
            }
        };
        when(appointmentRepository.findAll()).thenReturn(lista);
        service.getAll();
        verify(appointmentRepository,times(1)).findAll();
    }

    @Test
    void reserveAppointment_USER() throws ReservationExistsException {
        String role="USER";
        UUID id=UUID.randomUUID();
        Classroom classroomToReturn=new Classroom(1L);
        Employee employeeToReturn=new Employee("test","test",new EmployeeDepartment(1L,"test"),new EducationTitle(1L,"test"),new EmployeeType(1L,"test"),"test@gmaiol.com",null);
        UserProfile profileToReturn=new UserProfile(id,"test@gmail.com","1234",new UserRole(Constants.USER_ID_ROLE,Constants.USER_NAME_TYPE_ROLE),employeeToReturn);
        List<String> rooms=new LinkedList<>() {
            {

            }
        };
        Appointment appointmentToSave=new Appointment(id,employeeToReturn,classroomToReturn,"test",new Date(),"test","test",23,12,14,new AppointmentStatus(STATUS_PENDING),new AppointmentType(2L));
        List<ReserveDTO> lista=new LinkedList<>(){{
           add(new ReserveDTO("test@gmail.com",1L,"test",new Date(),"test","test",23,12,14, STATUS_PENDING,2));
           add(new ReserveDTO("test@gmail.com",1L,"test",new Date(),"test","test",23,15,16,STATUS_PENDING,2));

        }};

        when(appointmentRepository.AppointmentAvailable(1L,new Date(),12,13)).thenReturn(rooms);
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(profileToReturn);
        when(appointmentRepository.save(appointmentToSave)).thenReturn(appointmentToSave);
        service.ReserveAppointment(lista,role);


    }@Test
    void reserveAppointment_ADMIN() throws ReservationExistsException {
        String role="ADMIN";
        UUID id=UUID.randomUUID();
        Classroom classroomToReturn=new Classroom(1L);
        Employee employeeToReturn=new Employee("test","test",new EmployeeDepartment(1L,"test"),new EducationTitle(1L,"test"),new EmployeeType(1L,"test"),"test@gmaiol.com",null);
        UserProfile profileToReturn=new UserProfile(id,"test@gmail.com","1234",new UserRole(ADMIN_ID_ROLE, ADMIN_NAME_TYPE_ROLE),employeeToReturn);
        List<String> rooms=new LinkedList<>() {
            {

            }
        };
        Appointment appointmentToSave=new Appointment(id,employeeToReturn,classroomToReturn,"test",new Date(),"test","test",23,12,14,new AppointmentStatus(STATUS_APPROVED),new AppointmentType(2L));
        List<ReserveDTO> lista=new LinkedList<>(){{
           add(new ReserveDTO("test@gmail.com",1L,"test",new Date(),"test","test",23,12,14, STATUS_APPROVED,2));
           add(new ReserveDTO("test@gmail.com",1L,"test",new Date(),"test","test",23,15,16,STATUS_APPROVED,2));

        }};

        when(appointmentRepository.AppointmentAvailable(1L,new Date(),12,13)).thenReturn(rooms);
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(profileToReturn);
        when(appointmentRepository.save(appointmentToSave)).thenReturn(appointmentToSave);
        service.ReserveAppointment(lista,role);


    }

    @Test
    void searchReservation() {
        List<Appointment> appointments=new LinkedList<>(){
            {
                add(new Appointment());
                add(new Appointment());
                add(new Appointment());

            }
        };

        SearchAppointmentDTO dto=new SearchAppointmentDTO(new Date(),1L,13);
        when(service.searchReservation(dto)).thenReturn(appointments);
        service.searchReservation(dto);
        verify(appointmentRepository,times(1)).searchReservationsByClassroomAndDate(dto.getClassroomId(),dto.getDate());
    }

    @Test
    void getForDate() {
        List<Appointment> appointments=new LinkedList<>(){
            {
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L)));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L)));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L)));

            }
        };
        Date toGetFor=new Date();
        when(appointmentRepository.findByDate(toGetFor)).thenReturn(appointments);
        service.getForDate(toGetFor);
        verify(appointmentRepository,times(1)).findByDate(toGetFor);
    }

    @Test
    void isClassroomAvailableAtDate_Not_Available() {
        RequestIsClassroomAvailableForDateDTO request=new RequestIsClassroomAvailableForDateDTO();
        List<String>  result=new LinkedList<>(){{
           add("we");
           add("we");
           add("we");
        }};
        when(appointmentRepository.AppointmentAvailable(request.getClassroomId(),request.getDate(),request.getStart_timeInHours(),request.getEnd_timeInHours())).thenReturn(result);
        assertFalse(service.IsClassroomAvailableAtDate(request));
    }
    @Test
    void isClassroomAvailableAtDate_Available() {
        RequestIsClassroomAvailableForDateDTO request=new RequestIsClassroomAvailableForDateDTO();
        List<String>  result=new LinkedList<>(){{

        }};
        when(appointmentRepository.AppointmentAvailable(request.getClassroomId(),request.getDate(),request.getStart_timeInHours(),request.getEnd_timeInHours())).thenReturn(result);
        assertTrue(service.IsClassroomAvailableAtDate(request));
    }

    @Test
    void updateReservation_Appointment_Confilct() {
        List<String>  result=new LinkedList<>(){{
            add("we");
            add("we");
            add("we");
        }};
        UpdateAppointmentDTO updateAppointmentDTO=new UpdateAppointmentDTO();
        doNothing().when(appointmentRepository).updateReservation(updateAppointmentDTO.getId(),updateAppointmentDTO.getClassroomId(),updateAppointmentDTO.getName(),updateAppointmentDTO.getDate(),updateAppointmentDTO.getDecription(),updateAppointmentDTO.getReason(),updateAppointmentDTO.getNumber_of_attendies(),updateAppointmentDTO.getStart_timeInHours(),updateAppointmentDTO.getEnd_timeInHours(),updateAppointmentDTO.getType(),new AppointmentStatus(STATUS_PENDING, APPOINTMENT_PENDING));
        when(appointmentRepository.AppointmentConflict(updateAppointmentDTO.getId(),updateAppointmentDTO.getClassroomId(),updateAppointmentDTO.getDate(),updateAppointmentDTO.getStart_timeInHours(),updateAppointmentDTO.getEnd_timeInHours())).thenReturn(result);
        assertThrows(ReservationExistsException.class,()->service.updateReservation(updateAppointmentDTO));
    }
    @Test
    void updateReservation_Appointment_Not_Confilcted() throws ReservationExistsException {
        List<String>  result=new LinkedList<>(){{

        }};
        UpdateAppointmentDTO updateAppointmentDTO=new UpdateAppointmentDTO();
        doNothing().when(appointmentRepository).updateReservation(updateAppointmentDTO.getId(),updateAppointmentDTO.getClassroomId(),updateAppointmentDTO.getName(),updateAppointmentDTO.getDate(),updateAppointmentDTO.getDecription(),updateAppointmentDTO.getReason(),updateAppointmentDTO.getNumber_of_attendies(),updateAppointmentDTO.getStart_timeInHours(),updateAppointmentDTO.getEnd_timeInHours(),updateAppointmentDTO.getType(),new AppointmentStatus(STATUS_PENDING, APPOINTMENT_PENDING));
        when(appointmentRepository.AppointmentConflict(updateAppointmentDTO.getId(),updateAppointmentDTO.getClassroomId(),updateAppointmentDTO.getDate(),updateAppointmentDTO.getStart_timeInHours(),updateAppointmentDTO.getEnd_timeInHours())).thenReturn(result);
        service.updateReservation(updateAppointmentDTO);
        verify(appointmentRepository,times(1)).updateReservation(updateAppointmentDTO.getId(),updateAppointmentDTO.getClassroomId(),updateAppointmentDTO.getName(),updateAppointmentDTO.getDate(),updateAppointmentDTO.getDecription(),updateAppointmentDTO.getReason(),updateAppointmentDTO.getNumber_of_attendies(),updateAppointmentDTO.getStart_timeInHours(),updateAppointmentDTO.getEnd_timeInHours(),updateAppointmentDTO.getType(),new AppointmentStatus(STATUS_PENDING, APPOINTMENT_PENDING));

    }

    @Test
    void getForDateAndClassroom() {
        RequestAppointmetDateForClassroomDTO request=new RequestAppointmetDateForClassroomDTO(new Date(),2L);
        List<Appointment> appointments=new LinkedList<>(){
            {
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));

            }
        };
        List<GetForDateAppointmentDTO> result=new LinkedList<>(){{
           add(new GetForDateAppointmentDTO(22,22,"test","test","test"));
           add(new GetForDateAppointmentDTO(22,22,"test","test","test"));
           add(new GetForDateAppointmentDTO(22,22,"test","test","test"));
        }};
        when(appointmentRepository.findByDateAndClassroom(request.getDate(),request.getClassroomId())).thenReturn(appointments);
        service.getForDateAndClassroom(request);
        verify(appointmentRepository,times(1)).findByDateAndClassroom(request.getDate(),request.getClassroomId());
        assertEquals(result, service.getForDateAndClassroom(request));
    }

    @Test
    void declineAppointment_Not_Found()  {
        UUID id=UUID.randomUUID();
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

      assertThrows(AppointmentDoesNotExistsException.class,()->service.DeclineAppointment(id));

    }
    @Test
    void declineAppointment_Found() throws AppointmentDoesNotExistsException {
        UUID id=UUID.randomUUID();

        Appointment appointment=mock(Appointment.class);
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);


        service.DeclineAppointment(id);
        verify(appointmentRepository,times(1)).findById(id);
        verify(appointmentRepository,times(1)).save(appointment);
        verify(appointment,times(1)).setStatus(new AppointmentStatus(STATUS_DECLINED));

    }

    @Test
    void confirmAppointment_Not_Found() {
        UUID id=UUID.randomUUID();
        when(appointmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AppointmentDoesNotExistsException.class,()->service.ConfirmAppointment(id));

    }
    @Test
    void confirmAppointment_Found() throws AppointmentDoesNotExistsException {
        UUID id=UUID.randomUUID();

        Appointment appointment=mock(Appointment.class);
        when(appointmentRepository.findById(id)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);


        service.ConfirmAppointment(id);
        verify(appointmentRepository,times(1)).findById(id);
        verify(appointmentRepository,times(1)).save(appointment);
        verify(appointment,times(1)).setStatus(new AppointmentStatus(STATUS_APPROVED));

    }


}