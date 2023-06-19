package kpo.delivery.repos;

import kpo.delivery.domain.Dish;
import kpo.delivery.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAllByRestaurant(Restaurant restaurant);
}
