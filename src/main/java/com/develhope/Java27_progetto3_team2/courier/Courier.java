package com.develhope.Java27_progetto3_team2.courier;

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
    private String name;
    @Setter
    private String surname;
    @Setter
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Setter
    private String email;

    // Stato attuale del corriere (Available, Out for delivery, Out of service)
    @Setter
    private String status; //Temporaneo in attesa che un enumerator degli status venga implementato

/* Altre possibili implementazioni
    // Numero di ordini completati dal corriere
    private int completedOrders;
    // Valutazione media delle consegne effettuate dal corriere
    private Double averageRating;
*/

}
