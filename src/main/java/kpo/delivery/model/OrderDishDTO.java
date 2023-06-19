package kpo.delivery.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDishDTO {

    private Long id;

    @NotNull
    private Long quantity;

    @NotNull
    private Long dish;

    @NotNull
    private Long order;

}
