package kpo.delivery.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderModel {
    private List<OrderDishModel> dishes;
}
