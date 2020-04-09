package com.algafood.api.algafooddelivery.api.controller;

import com.algafood.api.algafooddelivery.domain.exception.EntityNotFoundException;
import com.algafood.api.algafooddelivery.domain.model.Kitchen;
import com.algafood.api.algafooddelivery.domain.model.Restaurant;
import com.algafood.api.algafooddelivery.domain.repository.RestaurantRepository;
import com.algafood.api.algafooddelivery.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/restaurants")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantService restaurantService) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
    }

    /* Must return a restaurant list */
    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    /* Must return a kitchen by ID */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);

        if (restaurant != null) {
            return ResponseEntity.status(HttpStatus.OK).body(restaurant);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    /* Must create a new restaurant */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Restaurant restaurant) {
        try {
            restaurant = restaurantService.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    /* Must update a kitchen by ID */
    @PutMapping("/{restaurantId}")
    public ResponseEntity<?> update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        try {

            Restaurant currentRestaurant = restaurantRepository.get(restaurantId);

            if (currentRestaurant != null) {
                BeanUtils.copyProperties(restaurant, currentRestaurant, "id");

                currentRestaurant = restaurantService.save(currentRestaurant);
                return ResponseEntity.ok(currentRestaurant);

            }

            return ResponseEntity.notFound().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{restaurantId}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields) {

        Restaurant currentRestaurant = restaurantRepository.get(restaurantId);

        if (currentRestaurant == null) {
            return ResponseEntity.notFound().build();
        }

        merge(fields, currentRestaurant);

        return update(restaurantId, currentRestaurant);

    }

    private void merge(@RequestBody Map<String, Object> originFields, Restaurant destinationRestaurant) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant originRestaurant = objectMapper.convertValue(originFields, Restaurant.class);

        originFields.forEach((propertiesName, propertiesValue) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, propertiesName);
            field.setAccessible(true);

            Object newValue = ReflectionUtils.getField(field, originRestaurant);

            ReflectionUtils.setField(field, destinationRestaurant, newValue);

        });
    }


}
