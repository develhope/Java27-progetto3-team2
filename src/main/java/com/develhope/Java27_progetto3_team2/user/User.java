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
    @Column(name = "name")
    private String name;
    @Setter
    @Column(name = "surname")
    private String surname;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private String email;
    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
