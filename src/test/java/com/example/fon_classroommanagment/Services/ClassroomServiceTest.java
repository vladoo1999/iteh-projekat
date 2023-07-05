package com.example.fon_classroommanagment.Services;

import com.example.fon_classroommanagment.Exceptions.ClassroomExistsException;
import com.example.fon_classroommanagment.Exceptions.UserExistsExcetion;
import com.example.fon_classroommanagment.Models.Appointment.Appointment;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentStatus;
import com.example.fon_classroommanagment.Models.Appointment.AppointmentType;
import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.example.fon_classroommanagment.Models.Classroom.ClassroomType;
import com.example.fon_classroommanagment.Models.DTO.ClassroomChipDTO;
import com.example.fon_classroommanagment.Models.DTO.classroom.FilterDTO;
import com.example.fon_classroommanagment.Models.DTO.classroom.RequestIsClassroomAvailableForDateDTO;
import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import com.example.fon_classroommanagment.Models.User.UserRole;
import com.example.fon_classroommanagment.Repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

import static com.example.fon_classroommanagment.Configuration.Constants.CHIP_SEARCH_ELEMENTS;
import static com.example.fon_classroommanagment.Configuration.Constants.PAGE_SIZE;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClassroomServiceTest {

     private  static ClassroomService service;

     private static ClassroomRepository classroomRepository;

    private  static AppointmentRepository appointmentRepository;

    private static UserRoleRepository userRoleRepository;

    @BeforeAll
    static void Start(){
        classroomRepository = mock(ClassroomRepository.class);
        appointmentRepository=mock(AppointmentRepository.class);
        userRoleRepository = mock(UserRoleRepository.class);
        service = new ClassroomService(classroomRepository,appointmentRepository,userRoleRepository);
    }


    @Test
    void searchClassroom() {
        int page = 1;
        String name = "201";

        List<Classroom> classrooms =new LinkedList<>(){
            {
                add(new Classroom(1L,"C201",11,10,false,false,0,0,0,new ClassroomType(1L,"obicna sala")));
                add(new Classroom(2L,"D201",22,22,true,true,22,2,2,new ClassroomType(1L,"test")));


            }
        };
        Page<Classroom> classroomsPage = new PageImpl<>(classrooms);
        when(classroomRepository.findByNameContaining("%"+name+"%", PageRequest.of(page-1,PAGE_SIZE))).thenReturn(classroomsPage);
        assertEquals(classroomRepository.findByNameContaining("%"+name+"%", PageRequest.of(page-1,PAGE_SIZE)),classroomsPage);
        verify(classroomRepository,times(1)).findByNameContaining("%"+name+"%", PageRequest.of(page-1,PAGE_SIZE));

    }

    @Test
    void classroomDetails() {
        Long id = 1L;
        Classroom classroom =  new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test"));
        Optional<Classroom> optional = Optional.of(classroom);


        List<Appointment> appointments=new LinkedList<>(){
            {
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));

            }
        };


        LocalDate date = LocalDate.now();
        System.out.println(new Date());
        when(appointmentRepository.reservationsByMonths(id,date.getMonthValue())).thenReturn(3);
        when(classroomRepository.findById(id)).thenReturn(optional);
    }


    @Test
    void classroomDetails_ClassroomDoesntExist() {
        Classroom classroom =  new Classroom(2L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test"));



        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.empty());
       assertThrows(ClassroomExistsException.class,()->service.classroomDetails(classroom.getId()));
    }

    @Test
    void getAllClassrooms() {
        int page = 1;
        FilterDTO dto = new FilterDTO(11,20,List.of(new ClassroomType(1L,"test")),true,true,true);


        List<Classroom> classrooms =new LinkedList<>(){
            {

                add(new Classroom(1L,"C201",12,20,true,true,0,0,0,new ClassroomType(1L,"test")));
                add(new Classroom(2L,"D201",12,20,true,true,22,2,2,new ClassroomType(1L,"test")));


            }
        };

        Page<Classroom> classroomsPage = new PageImpl<>(classrooms);
       when(classroomRepository.findAllSortedCapacity(PageRequest.of(page, PAGE_SIZE),dto.getMin_capacity(),dto.getMax_capacity() ,dto.isAircondition(),dto.isProjector(),dto.getTypes())).thenReturn(classroomsPage);
        assertEquals(classroomRepository.findAllSortedCapacity(PageRequest.of(page, PAGE_SIZE),dto.getMin_capacity(),dto.getMax_capacity() ,dto.isAircondition(),dto.isProjector(),dto.getTypes()),classroomsPage);
        verify(classroomRepository,times(1)).findAllSortedCapacity(PageRequest.of(page, PAGE_SIZE),dto.getMin_capacity(),dto.getMax_capacity() ,dto.isAircondition(),dto.isProjector(),dto.getTypes());
    }


    @Test
    void getForDateClassroom() {
        RequestIsClassroomAvailableForDateDTO dto = new RequestIsClassroomAvailableForDateDTO(new Date(),1L,12,14);
        Classroom classroom =  new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test"));
        Optional<Classroom> optional = Optional.of(classroom);

        List<Appointment> appointments=new LinkedList<>(){
            {
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));
                add(new Appointment(UUID.randomUUID(),new Employee(),new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test")),"test",new Date(),"test","test",22,22,22,new AppointmentStatus(1L),new AppointmentType(1L,"test")));

            }
        };


        when(classroomRepository.findById(dto.getClassroomId())).thenReturn(optional);
        when(appointmentRepository.findByDateAndClassroom(dto.getDate(),dto.getClassroomId())).thenReturn(appointments);


    }



    @Test
    void getForDateClassroomDoesntExist() {
        RequestIsClassroomAvailableForDateDTO dto = new RequestIsClassroomAvailableForDateDTO(new Date(),1L,12,14);
        Classroom classroom =  new Classroom(1L,"test",22,22,true,true,22,2,2,new ClassroomType(1L,"test"));
        Optional<Classroom> optional = Optional.of(classroom);



        when(classroomRepository.findById(dto.getClassroomId())).thenReturn(Optional.empty());
        assertThrows(ClassroomExistsException.class,()->service.getForDateClassroom(dto));


    }

    @Test
    void getAllClassroomTypes() {
        List<ClassroomType> classroomTypes =new LinkedList<>(){
            {
                add(new ClassroomType(1L,"RC"));
                add(new ClassroomType(1L,"amfiteatar"));
                add(new ClassroomType(1L,"citaonica"));

            }
        };
        when(classroomRepository.getAllClassroomTypes()).thenReturn(classroomTypes);
        assertEquals(classroomRepository.getAllClassroomTypes(),classroomTypes);
        verify(classroomRepository,times(1)).getAllClassroomTypes();
    }

    @Test
    void getAllAppointmentTypes() {
        List<AppointmentType> appointmentTypes =new LinkedList<>(){
            {
                add(new AppointmentType(1L,"ispit"));
                add(new AppointmentType(1L,"kolokvijum"));
                add(new AppointmentType(1L,"predavanje"));
            }
        };

        when(appointmentRepository.getAllAppointmentTypes()).thenReturn(appointmentTypes);
        assertEquals(appointmentRepository.getAllAppointmentTypes(),appointmentTypes);
        verify(appointmentRepository,times(1)).getAllAppointmentTypes();
    }

    @Test
    void getClassroomsAsChips() {
        String name = "201";

        List<Classroom> classrooms =new LinkedList<>(){
            {

                add(new Classroom(1L,"C201",12,20,true,true,0,0,0,new ClassroomType(1L,"test")));
                add(new Classroom(2L,"D201",12,20,true,true,22,2,2,new ClassroomType(1L,"test")));


            }
        };
        Page<Classroom> classroomsPage = new PageImpl<>(classrooms);
        when(classroomRepository.findByNameChips("%"+name+"%", Pageable.ofSize(CHIP_SEARCH_ELEMENTS))).thenReturn(classroomsPage);
        service.getClassroomsAsChips(name);
        verify(classroomRepository,times(1)).findByNameChips("%"+name+"%", Pageable.ofSize(CHIP_SEARCH_ELEMENTS));
        assertEquals(classroomRepository.findByNameChips("%"+name+"%", Pageable.ofSize(CHIP_SEARCH_ELEMENTS)),classroomsPage);
    }


    @Test
    void getAllUserRoles() {
        List<UserRole> roles =new LinkedList<>(){
            {

                add(new UserRole(1L,"ADMIN"));
                add(new UserRole(1L,"USER"));

            }
        };

        when(userRoleRepository.findAll()).thenReturn(roles);
        service.getAllUserRoles();
        verify(userRoleRepository,times(1)).findAll();

    }
}