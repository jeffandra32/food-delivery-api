package com.algafood.api.algafooddelivery.domain.service;

import com.algafood.api.algafooddelivery.domain.exception.EntityNotFoundException;
import com.algafood.api.algafooddelivery.domain.exception.EntityUsingException;
import com.algafood.api.algafooddelivery.domain.model.Kitchen;
import com.algafood.api.algafooddelivery.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class KitchenService {

    @Autowired
    private KitchenRepository kitchenRepository;

    public Kitchen save(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    public void remove(Long id) {
        try {
            kitchenRepository.remove(id);
        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(
                    String.format("Não existe um cadastro de cozinha com código %d", id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityUsingException(
                    String.format("Cozinha de código %d não pode ser removida pois está em uso.", id)
            );
        }
    }


}
