package com.example.fon_classroommanagment.Repository;

import com.example.fon_classroommanagment.Models.Classroom.Classroom;
import com.example.fon_classroommanagment.Models.Classroom.ClassroomType;
import com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomNamesDTO;
import com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomTableDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Positive;
import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom,Long>, PagingAndSortingRepository<Classroom,Long>{

@Query(value = "select  c from Classroom  c where  c.number_of_seats BETWEEN :min_capacity AND :max_capacity  and c.aircondition=:airconditionParam and c.projector=:projectorParam")
List<Classroom> filter(@Param("min_capacity") int min_capacity, @Param("max_capacity") int max_capacity,@Param("airconditionParam") boolean airconditionParam,@Param("projectorParam") boolean projectorParam);

    @Query(value = "select  c from Classroom  c where  c.number_of_seats BETWEEN :min_capacity AND :max_capacity and  c.type.id=:type")
    List<Classroom> filterWithType(@Param("min_capacity") int min_capacity, @Param("max_capacity") int max_capacity, @Param("type") Long type);

    @Query(value = "select  c from Classroom  c where  c.number_of_seats BETWEEN :min_capacity AND :max_capacity and  c.type.id=:type order by c.number_of_seats")
    List<Classroom> filterAll(@Param("min_capacity") int min_capacity, @Param("max_capacity") int max_capacity,@Param("type") Long type);

    @Query(value = "select  c from  Classroom  c where c.name like :name   ")
    Page<Classroom> findByNameContaining(@Param("name") String name,Pageable pageable);

    @Query(value = "select c from ClassroomType c")
    List<ClassroomType> getAllClassroomTypes();

    @Query(value = "select  c from  Classroom  c where c.name like :name   ")
    Page<Classroom> findByNameChips(@Param("name") String name,Pageable pageable);
    @Query(value = "select  c from  Classroom  c where c.name like :name   ")
    List<Classroom> findByNameChipsAll(@Param("name") String name);
    @Query(value = "select  c from Classroom  c where  c.number_of_seats BETWEEN :min_capacity AND :max_capacity  and c.aircondition=:aircondition and c.projector=:projector and  c.type in :types")

    Page<Classroom> findAll(Pageable pageable,@Param("min_capacity") int min_capacity,@Param("max_capacity") int max_capacity,@Param("aircondition") boolean aircondition,@Param("projector") boolean projector,@Param("types") List<ClassroomType> types);
    @Query(value = "select  c from Classroom  c where  c.number_of_seats BETWEEN :min_capacity AND :max_capacity  and c.aircondition=:aircondition and c.projector=:projector and c.type in :types order by  c.number_of_seats")

    Page<Classroom> findAllSortedCapacity(Pageable pageable, int min_capacity, int max_capacity, boolean aircondition, boolean projector,@Param("types") List<ClassroomType> types);

    @Query(value = "select new com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomNamesDTO(c.id,c.name) from Classroom c ")
    List<ClassroomNamesDTO> findAllByIdAndName();

    @Query(value = "select new com.example.fon_classroommanagment.Models.DTO.classroom.ClassroomTableDTO(c.id,c.name,c.type.name,c.number_of_seats) from Classroom c ")
    List<ClassroomTableDTO> getClassroomTable();
}
