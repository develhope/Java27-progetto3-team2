package com.develhope.Java27_progetto3_team2.review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateReviewDTO {

    @NotNull(message = "L'ID del ristorante è obbligatorio")
    private Long restaurantId;

    @Min(value = 1, message = "Il punteggio minimo deve essere 1")
    @Max(value = 5, message = "Il punteggio massimo deve essere 5")
    private int rating;

    private String comment;
}
