package com.develhope.Java27_progetto3_team2.order;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "`order`")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    // Identificatore unico per ogni ordine
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Stato corrente dell'ordine (Esempi: Pending, In Preparation, Delivering, Completed ecc.)
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

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


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // Lista di piatti che compongono l'ordine, rappresentati da MenuItem
    @ManyToMany
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private List<MenuItem> items;


}
