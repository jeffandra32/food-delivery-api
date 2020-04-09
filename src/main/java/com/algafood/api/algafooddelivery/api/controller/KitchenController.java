package com.algafood.api.algafooddelivery.api.controller;

import com.algafood.api.algafooddelivery.domain.exception.EntityNotFoundException;
import com.algafood.api.algafooddelivery.domain.exception.EntityUsingException;
import com.algafood.api.algafooddelivery.domain.model.Kitchen;
import com.algafood.api.algafooddelivery.domain.repository.KitchenRepository;
import com.algafood.api.algafooddelivery.domain.service.KitchenService;
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
    private KitchenService kitchenService;

    public KitchenController(KitchenRepository kitchenRepository, KitchenService kitchenService) {
        this.kitchenRepository = kitchenRepository;
        this.kitchenService = kitchenService;
    }

    /* Must return a kitchen list */
    @GetMapping
    public List<Kitchen> getAll() {
        return kitchenRepository.getAll();
    }

    /* Must return a kitchen by ID */
    @GetMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> getById(@PathVariable Long kitchenId) {
        Kitchen kitchen = kitchenRepository.get(kitchenId);

        if (kitchen != null) {
            return ResponseEntity.status(HttpStatus.OK).body(kitchen);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    /* Must create a new kitchen */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen create(@RequestBody Kitchen kitchen) {
        return kitchenService.save(kitchen);
    }

    /* Must update a kitchen by ID */
    @PutMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> update(@PathVariable Long kitchenId, @RequestBody Kitchen kitchen) {
        Kitchen currentKitchen = kitchenRepository.get(kitchenId);

        if (currentKitchen != null) {
            BeanUtils.copyProperties(kitchen, currentKitchen, "id");

            currentKitchen = kitchenService.save(currentKitchen);

            return ResponseEntity.ok(currentKitchen);
        }

        return ResponseEntity.notFound().build();

    }

    /* Must remove a kitchen by ID */
    @DeleteMapping("/{kitchenId}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long kitchenId) {
        try {
            kitchenService.remove(kitchenId);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (EntityUsingException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
