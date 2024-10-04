package com.develhope.Java27_progetto3_team2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MenuItem {
    private int id;
    @Setter
    private String itemName;
    @Setter
    private String itemDescription;
    @Setter
    private double itemPrice;
    @Setter
    private boolean availability;
    @Setter
    private boolean vegetarianItem;
    @Setter
    private boolean glutenFreeItem;
    @Setter
    private boolean lactoseFreeItem;
    private int menuId;
}
