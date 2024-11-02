package com.develhope.Java27_progetto3_team2.user;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private Role role;
    private String email;
    private String password;
    private String phoneNumber;
}
