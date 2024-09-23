package com.example.demo.exception;

import com.example.demo.domain.command.CommandFailure;
import com.example.demo.messages.MessageResolver;

import java.util.List;


public abstract class BaseException extends AbstractRestException {

    protected BaseException(CommandFailure commandFailure) {

        super(
                commandFailure.getCode(),
                null,
                commandFailure.getErrors(),
                MessageResolver.getMessage(commandFailure.getCode(), commandFailure.getArguments().toArray()));
    }

    protected BaseException(String code, Throwable exception, String message) {

        super(code, exception, message);
    }

    protected <T extends Error> BaseException(String code, Throwable exception,
                                              List<T> errors, String message) {

        super(code, exception, errors, message);
    }

    protected BaseException(String code) {

        super(code);
    }

    protected BaseException(String code, String message) {

        super(code, message);
    }

    protected BaseException(String code, Object... arguments) {

        super(code, arguments);
    }

}