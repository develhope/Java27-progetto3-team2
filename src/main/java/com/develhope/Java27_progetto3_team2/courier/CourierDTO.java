package com.develhope.Java27_progetto3_team2.courier;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CourierDTO {

    private Long id;
    @Setter
    private String name;
    @Setter
    private String surname;

    @NotNull(message = "Phone number cannot be null")
    @Setter
    private String phoneNumber;
    @Setter
    private String email;
    @Setter
    private CourierStatus status; // In attesa dell'enumerator
    @Setter
    private List<Long> orderList;

}
