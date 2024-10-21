package com.develhope.Java27_progetto3_team2.menu;

import com.develhope.Java27_progetto3_team2.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity(name = "menu_item")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(name = "item_name", nullable= false)
    private String itemName;
    @Setter
    @Column(name = "item_description")
    private String itemDescription;
    @Setter
    @Column(name = "category_food")
    private String categoryFood;
    @Setter
    @Column(name = "item_price", nullable= false)
    private double itemPrice;
    @Setter
    @Column(name = "availability")
    private boolean availability;
    @Setter
    @Column(name = "vegetarian_item")
    private boolean vegetarianItem;
    @Setter
    @Column(name = "gluten_free_item")
    private boolean glutenFreeItem;
    @Setter
    @Column(name = "lactose_free_item")
    private boolean lactoseFreeItem;
    @ManyToOne
    @JoinColumn(name = "restaurant_menu", nullable = false)
    private RestaurantMenu restaurantMenu;

    @ManyToMany(mappedBy = "items")
    private List<Order> orders;

}

