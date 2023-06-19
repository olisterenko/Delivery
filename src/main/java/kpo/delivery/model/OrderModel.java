package kpo.delivery.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderModel {
    @NotEmpty
    private List<OrderDishModel> dishes;
}
