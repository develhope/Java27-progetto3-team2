package com.develhope.Java27_progetto3_team2.courier;

import com.develhope.Java27_progetto3_team2.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity(name = "courier")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    private CourierStatus status; //Temporaneo in attesa che un enumerator degli status venga implementato

    @OneToMany(mappedBy = "courierId")
    private List<Order> orderList;
}
