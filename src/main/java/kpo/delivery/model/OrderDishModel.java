package kpo.delivery.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishModel {
    @NotNull
    private Long quantity;

    @NotNull
    private Long dish;
}
