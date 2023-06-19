package kpo.delivery.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import kpo.delivery.model.DishModel;
import kpo.delivery.model.DishDTO;
import kpo.delivery.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class Menu {

    private final DishService dishService;

    public Menu(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishs() {
        return ResponseEntity.ok(dishService.findAll());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<DishModel>> getMenu(@PathVariable(name = "restaurantId") final Long restaurantId) {
        return ResponseEntity.ok(dishService.findAllByRestaurant(restaurantId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDish(@RequestBody @Valid final DishModel dishModel) {
        final Long createdId = dishService.create(dishModel);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

}
