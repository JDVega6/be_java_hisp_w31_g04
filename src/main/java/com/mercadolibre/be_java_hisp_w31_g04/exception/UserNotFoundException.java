package com.mercadolibre.be_java_hisp_w31_g04.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("No se encontró ningún usuario con ese ID");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
