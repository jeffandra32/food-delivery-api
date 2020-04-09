package com.algafood.api.algafooddelivery.domain.repository;

import com.algafood.api.algafooddelivery.domain.model.Kitchen;

import java.util.List;

public interface KitchenRepository {

    List<Kitchen> getAll();

    Kitchen get(Long id);

    Kitchen save(Kitchen kitchen);

    void remove(Kitchen kitchen);
}
