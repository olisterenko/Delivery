package kpo.delivery.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishModel {
    @NotNull
    @Positive
    private Long quantity;

    @NotNull
    private Long dish;
}
