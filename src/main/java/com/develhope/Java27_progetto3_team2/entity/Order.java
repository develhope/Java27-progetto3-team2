package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Entity(name = "order")
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    // Identificatore unico per ogni ordine
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Stato corrente dell'ordine (Esempi: Pending, In Preparation, Delivering, Completed ecc.)
    @Setter
    @Column(name = "status", nullable = false)
    private String status; //Temporaneo in attesa che un enumerator degli status venga implementato

    // Indirizzo di consegna fornito dal cliente
    @Setter
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    // Metodo di pagamento scelto dal cliente (Esempi: Carta di credito, PayPal)
    @Setter
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    // Data di creazione dell'ordine
    @Setter
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    // Prezzo totale dell'ordine
    @Setter
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    // ID del corriere assegnato all'ordine
    @Setter
    @Column(name = "courier_id", nullable = false)
    private Long courierId; //Temporaneo, in definizione se creare un'entità separata per il corriere


/* Attributi che deriveranno dai join con le altre entity
    // Cliente che effettua ordine
    @Column(name = "user_id", nullable = false)
    private User user;

    // Ristorante del quale viene effettuato l'ordine
    @Column(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // Lista di piatti che compongono l'ordine, rappresentati da MenuItem
    @Column(name = "order_id")
    private List<MenuItem> items;
*/

}
