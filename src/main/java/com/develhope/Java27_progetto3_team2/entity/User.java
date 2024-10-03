package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(name = "name")
    private String name;
    @Setter
    @Column(name = "surname")
    private String surname;
    @Setter
    @Column(name = "role")
    private String role; //Temporary untill an enumerator with roles will get implemented
}
