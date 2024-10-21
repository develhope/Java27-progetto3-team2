package com.develhope.Java27_progetto3_team2.user;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "user")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String surname;
    @Setter
    private String role; //Temporary untill an enumerator with roles will get implemented
}
