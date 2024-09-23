package com.example.demo.exception;

import com.example.demo.domain.command.CommandFailure;

import java.util.List;

public class DaoException extends BaseException {

    public DaoException(CommandFailure commandFailure) {

        super(commandFailure);
    }

    public DaoException(String code, Throwable exception, String message) {

        super(code, exception, message);
    }

    public <T extends Error> DaoException(String code, Throwable exception, List<T> errors, String message) {

        super(code, exception, errors, message);
    }

    public DaoException(String code) {

        super(code);
    }

    public DaoException(String code, String message) {

        super(code, message);
    }

    public DaoException(String code, Object... arguments) {

        super(code, arguments);
    }

}
