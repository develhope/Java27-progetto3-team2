package com.develhope.Java27_progetto3_team2.menu.model.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MenuItemDTO {
    private Long id;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private String categoryFood;
    private boolean availability;
    private boolean vegetarianItem;
    private boolean glutenFreeItem;
    private boolean lactoseFreeItem;
    private Long menuId;
}
