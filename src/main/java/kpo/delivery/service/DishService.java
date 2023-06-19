package kpo.delivery.service;

import java.util.List;

import kpo.delivery.domain.Dish;
import kpo.delivery.domain.Restaurant;
import kpo.delivery.model.DishModel;
import kpo.delivery.model.DishDTO;
import kpo.delivery.repos.DishRepository;
import kpo.delivery.repos.RestaurantRepository;
import kpo.delivery.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(final DishRepository dishRepository,
                       final RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<DishDTO> findAll() {
        final List<Dish> dishs = dishRepository.findAll(Sort.by("id"));
        return dishs.stream()
                .map(dish -> mapToDTO(dish, new DishDTO()))
                .toList();
    }

    public DishDTO get(final Long id) {
        return dishRepository.findById(id)
                .map(dish -> mapToDTO(dish, new DishDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DishModel dishModel) {
        final Dish dish = new Dish();
        mapToEntity(dishModel, dish);
        return dishRepository.save(dish).getId();
    }

    private DishDTO mapToDTO(final Dish dish, final DishDTO dishDTO) {
        dishDTO.setId(dish.getId());
        dishDTO.setName(dish.getName());
        dishDTO.setDescription(dish.getDescription());
        dishDTO.setPrice(dish.getPrice());
        dishDTO.setRestaurant(dish.getRestaurant().getId());
        return dishDTO;
    }

    private Dish mapToEntity(final DishModel dishModel, final Dish dish) {
        dish.setName(dishModel.getName());
        dish.setDescription(dishModel.getDescription());
        dish.setPrice(dishModel.getPrice());
        final Restaurant restaurant = restaurantRepository.findById(dishModel.getRestaurant())
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        dish.setRestaurant(restaurant);
        return dish;
    }

    private DishModel mapToModel(final Dish dish, final DishModel dishModel) {
        dishModel.setName(dish.getName());
        dishModel.setDescription(dish.getDescription());
        dishModel.setPrice(dish.getPrice());
        dishModel.setRestaurant(dish.getRestaurant().getId());
        return dishModel;
    }

    public List<DishModel> findAllByRestaurant(Long restaurantId) {
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        final List<Dish> dishes = dishRepository.findAllByRestaurant(restaurant);
        return dishes.stream()
                .map(dish -> mapToModel(dish, new DishModel()))
                .toList();
    }

}
