package com.develhope.Java27_progetto3_team2.user;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    @Setter
    private String name;
    @Setter
    private String surname;
    @Setter
    private String role;
}
