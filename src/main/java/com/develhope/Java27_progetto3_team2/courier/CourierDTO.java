package com.develhope.Java27_progetto3_team2.courier;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Setter
    private List<Long> orderList;

}
