package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "courier")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome, cognome, telefono e mail del corriere
    @Setter
    @Column(name = "name", nullable = false)
    private String name;
    @Setter
    @Column(name = "surname")
    private String surname;
    @Setter
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Stato attuale del corriere (Available, Out for delivery, Out of service)
    @Setter
    @Column(name = "status", nullable = false)
    private String status; //Temporaneo in attesa che un enumerator degli status venga implementato

/* Altre possibili implementazioni
    // Numero di ordini completati dal corriere
    private int completedOrders;
    // Valutazione media delle consegne effettuate dal corriere
    private Double averageRating;
*/

}
