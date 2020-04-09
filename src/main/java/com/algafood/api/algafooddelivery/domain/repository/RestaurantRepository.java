package com.algafood.api.algafooddelivery.domain.repository;

import com.algafood.api.algafooddelivery.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getAll();
    Restaurant get(Long id);
    Restaurant save(Restaurant restaurant);
    void remove(Restaurant restaurant);

}
