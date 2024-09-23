package com.example.demo.exception;

import com.example.demo.domain.command.CommandFailure;
import com.example.demo.messages.MessageResolver;

import java.util.List;

/**
 * Excepción usada en la capa de dominio
 */
public class DomainException extends BaseException {

    private static final long serialVersionUID = 7339418228172763600L;

    public DomainException(CommandFailure commandFailure) {

        super(commandFailure);
    }

    /**
     * Constructor de la clase DomainException
     *
     * @param code código de la excepción
     */
    public DomainException(String code) {

        super(code, null, MessageResolver.getMessage(code));
    }

    /**
     * Constructor de la clase DomainException
     *
     * @param code    código de la excepción
     * @param message mensaje de la excepción
     */
    public DomainException(String code, String message) {

        super(code, null, message);
    }

    /**
     * Constructor de la clase DomainException
     *
     * @param code      código de la excepción
     * @param exception excepción
     * @param message   mensaje de la excepción
     */
    public DomainException(String code, Throwable exception, String message) {

        super(code, exception, message);
    }

    /**
     * Constructor de la clase DomainException
     *
     * @param code      código de la excepción
     * @param exception excepción
     * @param errors    lista de errores
     * @param message   mensaje de la excepción
     */
    public DomainException(String code, Throwable exception, List<Error> errors, String message) {

        super(code, exception, errors, message);
    }

    /**
     * Constructor de la clase DomainException
     *
     * @param code      código de la excepción
     * @param arguments argumentos de la excepción
     */
    public DomainException(String code, Object... arguments) {

        super(code, null, MessageResolver.getMessage(code, arguments));
    }
}