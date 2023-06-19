package kpo.delivery.service;

import java.util.List;
import kpo.delivery.domain.Dish;
import kpo.delivery.domain.Order;
import kpo.delivery.domain.OrderDish;
import kpo.delivery.model.OrderDishDTO;
import kpo.delivery.model.OrderDishModel;
import kpo.delivery.repos.DishRepository;
import kpo.delivery.repos.OrderDishRepository;
import kpo.delivery.repos.OrderRepository;
import kpo.delivery.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderDishService {

    private final OrderDishRepository orderDishRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    public OrderDishService(final OrderDishRepository orderDishRepository,
            final DishRepository dishRepository, final OrderRepository orderRepository) {
        this.orderDishRepository = orderDishRepository;
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderDishDTO> findAll() {
        final List<OrderDish> orderDishs = orderDishRepository.findAll(Sort.by("id"));
        return orderDishs.stream()
                .map(orderDish -> mapToDTO(orderDish, new OrderDishDTO()))
                .toList();
    }

    public OrderDishDTO get(final Long id) {
        return orderDishRepository.findById(id)
                .map(orderDish -> mapToDTO(orderDish, new OrderDishDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDishDTO orderDishDTO) {
        final OrderDish orderDish = new OrderDish();
        mapToEntity(orderDishDTO, orderDish);
        return orderDishRepository.save(orderDish).getId();
    }

    public void update(final Long id, final OrderDishDTO orderDishDTO) {
        final OrderDish orderDish = orderDishRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDishDTO, orderDish);
        orderDishRepository.save(orderDish);
    }

    public void delete(final Long id) {
        orderDishRepository.deleteById(id);
    }

    private OrderDishDTO mapToDTO(final OrderDish orderDish, final OrderDishDTO orderDishDTO) {
        orderDishDTO.setId(orderDish.getId());
        orderDishDTO.setQuantity(orderDish.getQuantity());
        orderDishDTO.setDish(orderDish.getDish() == null ? null : orderDish.getDish().getId());
        orderDishDTO.setOrder(orderDish.getOrder() == null ? null : orderDish.getOrder().getId());
        return orderDishDTO;
    }

    private OrderDish mapToEntity(final OrderDishDTO orderDishDTO, final OrderDish orderDish) {
        orderDish.setQuantity(orderDishDTO.getQuantity());
        final Dish dish = orderDishDTO.getDish() == null ? null : dishRepository.findById(orderDishDTO.getDish())
                .orElseThrow(() -> new NotFoundException("dish not found"));
        orderDish.setDish(dish);
        final Order order = orderDishDTO.getOrder() == null ? null : orderRepository.findById(orderDishDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        orderDish.setOrder(order);
        return orderDish;
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
