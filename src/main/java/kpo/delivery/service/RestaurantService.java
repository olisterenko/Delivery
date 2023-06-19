package kpo.delivery.service;

import java.util.List;
import kpo.delivery.domain.Restaurant;
import kpo.delivery.model.CreateRestaurantDTO;
import kpo.delivery.model.RestaurantDTO;
import kpo.delivery.repos.RestaurantRepository;
import kpo.delivery.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantDTO> findAll() {
        final List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by("id"));
        return restaurants.stream()
                .map(restaurant -> mapToDTO(restaurant, new RestaurantDTO()))
                .toList();
    }

    public RestaurantDTO get(final Long id) {
        return restaurantRepository.findById(id)
                .map(restaurant -> mapToDTO(restaurant, new RestaurantDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CreateRestaurantDTO createRestaurantDTO) {
        final Restaurant restaurant = new Restaurant();
        mapToEntity(createRestaurantDTO, restaurant);
        return restaurantRepository.save(restaurant).getId();
    }

    private RestaurantDTO mapToDTO(final Restaurant restaurant, final RestaurantDTO restaurantDTO) {
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setAdress(restaurant.getAdress());
        restaurantDTO.setRating(restaurant.getRating());
        return restaurantDTO;
    }

    private Restaurant mapToEntity(final CreateRestaurantDTO createRestaurantDTO, final Restaurant restaurant) {
        restaurant.setName(createRestaurantDTO.getName());
        restaurant.setAdress(createRestaurantDTO.getAdress());
        restaurant.setRating(createRestaurantDTO.getRating());
        return restaurant;
    }

}
