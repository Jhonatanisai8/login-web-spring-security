package com.isai.demologinspringboot.app.exceptions;

public class WarehouseException
        extends RuntimeException {
    public WarehouseException(String message) {
        super(message);
    }

    public WarehouseException(String message, Throwable cause) {
        super(message, cause);
    }

}
