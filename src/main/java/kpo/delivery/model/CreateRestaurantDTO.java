package kpo.delivery.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRestaurantDTO {
    @NotEmpty(message = "Name should not be blank")
    @Size(max = 255)
    private String name;

    @NotEmpty(message = "Adress should not be blank")
    @Size(max = 255)
    private String adress;

    @NotNull
    @Min(value = 0, message = "Rating should not be less than 0")
    @Max(value = 5, message = "Rating should not be greater than 5")
    private Double rating;
}
