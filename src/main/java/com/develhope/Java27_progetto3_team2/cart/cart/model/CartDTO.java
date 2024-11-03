package com.develhope.Java27_progetto3_team2.cart.cart.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private Long id;
    private Status status;
    private List<Long> cartItemIdList;
    private Long userId;
    private Long restaurantId;
}
