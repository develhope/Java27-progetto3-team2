package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class MenuItem {
    @Id
    private int id;
    @Setter
    @Column(name = "item_name", nullable= false)
    private String itemName;
    @Setter
    @Column(name = "item_description")
    private String itemDescription;
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
    @Column (name = "menu_id", nullable= false)
    private int menuId;
}
