package kpo.delivery.service;

import java.util.List;
import kpo.delivery.domain.Order;
import kpo.delivery.domain.OrderDish;
import kpo.delivery.model.OrderDishModel;
import kpo.delivery.repos.DishRepository;
import kpo.delivery.repos.OrderDishRepository;
import kpo.delivery.repos.OrderRepository;
import org.springframework.stereotype.Service;


@Service
public class OrderDishService {

    private final OrderDishRepository orderDishRepository;

    public OrderDishService(final OrderDishRepository orderDishRepository,
            final DishRepository dishRepository, final OrderRepository orderRepository) {
        this.orderDishRepository = orderDishRepository;
    }

    public List<OrderDishModel> findByOrder(Order order) {
        final List<OrderDish> orderDishs = orderDishRepository.findAllByOrder(order);
        return orderDishs.stream()
                .map(orderDish -> mapToModel(orderDish, new OrderDishModel()))
                .toList();
    }
    private OrderDishModel mapToModel(final OrderDish orderDish, final OrderDishModel orderDishModel) {
        orderDishModel.setQuantity(orderDish.getQuantity());
        orderDishModel.setDish(orderDish.getDish().getId());
        return orderDishModel;
    }
}
