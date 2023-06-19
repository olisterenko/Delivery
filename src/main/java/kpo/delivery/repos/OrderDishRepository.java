package kpo.delivery.repos;

import kpo.delivery.domain.Order;
import kpo.delivery.domain.OrderDish;
import kpo.delivery.model.OrderDishModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
    List<OrderDish> findAllByOrder(Order order);
}
