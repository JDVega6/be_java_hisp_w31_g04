package com.mercadolibre.be_java_hisp_w31_g04.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
