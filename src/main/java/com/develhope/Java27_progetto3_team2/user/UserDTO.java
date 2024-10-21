package com.develhope.Java27_progetto3_team2.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDTO {

    @Setter
    private Long id;
    @Setter
    private String name;
    @Setter
    private String surname;
    @Setter
    private String username;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private UserRole role;
}
