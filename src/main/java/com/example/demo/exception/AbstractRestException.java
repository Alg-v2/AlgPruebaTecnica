package com.example.demo.exception;

import com.example.demo.messages.MessageResolver;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.exception.model.Error;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta para destinada a la gestión de excepciones
 */
@Slf4j
public abstract class AbstractRestException extends RuntimeException {

    private static final long serialVersionUID = 2158306598324272678L;

    /**
     * Código del mensaje
     */
    protected final String code;

    /**
     * Lista de errores del mensaje
     */
    protected final transient List<? extends Error> errors;

    /**
     * Excepcion generica
     *
     * @param code      codigo para traducir el mensaje de error
     * @param exception exception
     * @param message   Mensaje por defecto cuado el mensaje no se encuentra en los recursos
     */
    protected AbstractRestException(String code, Throwable exception, String message) {

        super(message, exception);
        this.code = code;
        this.errors = new ArrayList<>();
    }

    /**
     * @param code      codigo para traducir el mensaje de error
     * @param exception exception
     * @param errors    lista de errores
     * @param message   Mensaje por defecto cuado el mensaje no se encuentra en los recursos
     */
    protected <T extends Error> AbstractRestException(String code, Throwable exception, List<T> errors,
                                                      String message) {

        super(message, exception);
        this.code = code;
        this.errors = errors;
    }

    protected AbstractRestException(String code) {

        this(code, null, MessageResolver.getMessage(code));
    }

    protected AbstractRestException(String code, String message) {

        this(code, null, message);
    }

    protected AbstractRestException(String code, Object... arguments) {

        this(code, null, MessageResolver.getMessage(code, arguments));
    }

    /**
     * Código de la excepción
     *
     * @return a {@link String} object.
     */
    public String getCode() {

        return this.code;
    }

    /**
     * Obtiene la lista de errores
     *
     * @return list of errors
     */
    public <T extends Error> List<T> getErrors() {

        return (List<T>)errors;
    }

}
