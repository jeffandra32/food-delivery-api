package com.algafood.api.algafooddelivery.domain.service;

import com.algafood.api.algafooddelivery.domain.exception.EntityNotFoundException;
import com.algafood.api.algafooddelivery.domain.model.Kitchen;
import com.algafood.api.algafooddelivery.domain.model.Restaurant;
import com.algafood.api.algafooddelivery.domain.repository.KitchenRepository;
import com.algafood.api.algafooddelivery.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    public Restaurant save(Restaurant restaurant) {
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRepository.get(kitchenId);

        if (kitchen == null) {
            throw new EntityNotFoundException(
                    String.format("Não existe cadastro de cozinha com código %d", kitchenId));
        }

        restaurant.setKitchen(kitchen);
        return restaurantRepository.save(restaurant);
    }


}
