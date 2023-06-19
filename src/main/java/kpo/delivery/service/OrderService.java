package kpo.delivery.service;

import java.util.List;

import kpo.delivery.domain.Dish;
import kpo.delivery.domain.Order;
import kpo.delivery.domain.OrderDish;
import kpo.delivery.model.OrderDTO;
import kpo.delivery.model.OrderModel;
import kpo.delivery.repos.DishRepository;
import kpo.delivery.repos.OrderDishRepository;
import kpo.delivery.repos.OrderRepository;
import kpo.delivery.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDishRepository orderDishRepository;
    private final DishRepository dishRepository;

    public OrderService(final OrderRepository orderRepository,
                        final OrderDishRepository orderDishRepository,
                        final DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.orderDishRepository = orderDishRepository;
        this.dishRepository = dishRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderModel orderModel) {
        final Order order = new Order();
        var id = orderRepository.save(order).getId();
        createDishOrders(orderModel, id);
        return id;
    }

    private void createDishOrders(OrderModel orderModel, Long orderid) {
        for (var orderDishModel : orderModel.getDishes()) {
            OrderDish orderDish = new OrderDish();
            final Dish dish = dishRepository.findById(orderDishModel.getDish())
                    .orElseThrow(() -> new NotFoundException("dish not found"));
            orderDish.setDish(dish);
            final Order order =  orderRepository.findById(orderid)
                    .orElseThrow(() -> new NotFoundException("order not found"));
            orderDish.setOrder(order);
            orderDish.setQuantity(orderDishModel.getQuantity());
            orderDishRepository.save(orderDish);
        }
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setDishes(orderDishRepository.findAllByOrder(order));
        return orderDTO;
    }

}
