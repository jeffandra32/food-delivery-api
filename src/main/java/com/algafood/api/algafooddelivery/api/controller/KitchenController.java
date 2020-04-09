package com.algafood.api.algafooddelivery.api.controller;

import com.algafood.api.algafooddelivery.domain.model.Kitchen;
import com.algafood.api.algafooddelivery.domain.repository.KitchenRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/kitchens")
public class KitchenController {

    private final KitchenRepository kitchenRepository;

    public KitchenController(KitchenRepository kitchenRepository) {
        this.kitchenRepository = kitchenRepository;
    }

    @GetMapping
    public List<Kitchen> getAll() {
        return kitchenRepository.getAll();
    }

    @GetMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> getById(@PathVariable Long kitchenId) {
        Kitchen kitchen = kitchenRepository.get(kitchenId);

        if (kitchen != null) {
            return ResponseEntity.status(HttpStatus.OK).body(kitchen);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen create(@RequestBody Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    @PutMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> update(@PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {
        Kitchen currentKitchen = kitchenRepository.get(kitchenId);

        if (currentKitchen != null) {
            BeanUtils.copyProperties(kitchen, currentKitchen, "id");

            currentKitchen = kitchenRepository.save(currentKitchen);

            return ResponseEntity.ok(currentKitchen);
        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long kitchenId) {

        try {
            Kitchen kitchen = kitchenRepository.get(kitchenId);

            if (kitchen != null) {
                kitchenRepository.remove(kitchen);

                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.notFound().build();

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
