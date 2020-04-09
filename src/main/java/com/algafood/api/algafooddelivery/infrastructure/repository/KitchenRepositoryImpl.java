package com.algafood.api.algafooddelivery.infrastructure.repository;

import com.algafood.api.algafooddelivery.domain.model.Kitchen;
import com.algafood.api.algafooddelivery.domain.repository.KitchenRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class KitchenRepositoryImpl implements KitchenRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Kitchen> getAll() {
        return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
    }

    @Override
    public Kitchen get(Long id) {
        return manager.find(Kitchen.class, id);
    }

    @Override
    @Transactional
    public Kitchen save(Kitchen kitchen) {
        return manager.merge(kitchen);
    }

    @Override
    @Transactional
    public void remove(Kitchen kitchen) {
        kitchen = get(kitchen.getId());
        manager.remove(kitchen);
    }
}
