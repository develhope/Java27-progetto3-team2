package com.develhope.Java27_progetto3_team2.courier;

import com.develhope.Java27_progetto3_team2.order.Order;
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

    // Nome, cognome, telefono e mail del corriere
    private String name;
    private String surname;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    private String email;

    // Stato attuale del corriere (Available, Out for delivery, Out of service)
    private String status; //Temporaneo in attesa che un enumerator degli status venga implementato

    @OneToMany(mappedBy = "courierId")
    private List<Order> orderList;
/* Altre possibili implementazioni
    // Numero di ordini completati dal corriere
    private int completedOrders;
    // Valutazione media delle consegne effettuate dal corriere
    private Double averageRating;
*/

}
