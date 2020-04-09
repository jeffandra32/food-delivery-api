package com.algafood.api.algafooddelivery.infrastructure.repository;

import com.algafood.api.algafooddelivery.domain.model.Restaurant;
import com.algafood.api.algafooddelivery.domain.repository.RestaurantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurant> getAll() {
        return manager.createQuery("from Restaurant", Restaurant.class).getResultList();
    }

    @Override
    public Restaurant get(Long id) {
        return manager.find(Restaurant.class, id);
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        return manager.merge(restaurant);
    }

    @Override
    @Transactional
    public void remove(Restaurant restaurant) {
        restaurant = get(restaurant.getId());
        manager.remove(restaurant);
    }
}
