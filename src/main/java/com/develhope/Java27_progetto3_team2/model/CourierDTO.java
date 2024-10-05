package com.develhope.Java27_progetto3_team2.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourierDTO {

    private Long id;
    @Setter
    private String name;
    @Setter
    private String surname;
    @Setter
    private String phoneNumber;
    @Setter
    private String email;
    @Setter
    private String status; // In attesa dell'enumerator

}
