package com.example.fon_classroommanagment.Models.User;

import com.example.fon_classroommanagment.Models.Emplayee.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

import static com.example.fon_classroommanagment.Configuration.Constants.USER_PROFITE_TABLE_NAME;


@Entity
@Table(name = USER_PROFITE_TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @Type(type = "uuid-char")
    @Column(columnDefinition = "VARCHAR(255)",nullable = false)
    private UUID id;

    @Column(columnDefinition = "VARCHAR(70)",nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(255)",nullable = false)
    private String password;

    //dodati su cascade da bi lakse testirali ,obiris posle
    @ManyToOne(optional = false)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;
}
