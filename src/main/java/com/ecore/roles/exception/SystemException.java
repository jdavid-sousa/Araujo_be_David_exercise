package com.ecore.roles.exception;

import java.util.UUID;

import static java.lang.String.format;

public class SystemException extends RuntimeException {

    public <T> SystemException(Class<T> resource, String msg) {
        super(msg);
    }
}
