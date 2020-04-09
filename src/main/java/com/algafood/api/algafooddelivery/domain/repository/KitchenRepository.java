package com.algafood.api.algafooddelivery.domain.repository;

import com.algafood.api.algafooddelivery.domain.model.Kitchen;

import javax.transaction.Transactional;
import java.util.List;

public interface KitchenRepository {

    List<Kitchen> getAll();
    Kitchen get(Long id);
    Kitchen save(Kitchen kitchen);
    @Transactional
    void remove(Long id);
}
