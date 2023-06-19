package kpo.delivery.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderDTO {

    private Long id;

    private List<OrderDishModel> dishes;
}
