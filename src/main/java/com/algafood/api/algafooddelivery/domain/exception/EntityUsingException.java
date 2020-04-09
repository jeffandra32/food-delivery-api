package com.algafood.api.algafooddelivery.domain.exception;

public class EntityUsingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityUsingException(String message) {
        super(message);
    }
}
