package com.example.demo.exception;

import com.example.demo.exception.enums.CommonMessageCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;


@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CommonExceptionHandler {

    private static final String DUPLICATED_KEY_MESSAGE = "duplicate key value violates unique constraint";

    private CommonExceptionHandler() {

    }

    public static <X extends RuntimeException> X handleException(Throwable t) throws X {

        log.error("CommonExceptionHandler - Message: {}", t.getMessage());

        return Match(t).of(
                Case($(instanceOf(DuplicateKeyException.class)), ex -> {
                    String message = ex.getMostSpecificCause().getLocalizedMessage();
                    throw new DaoException(
                            CommonMessageCodes.UNIQUE_CONSTRAINT_VIOLATED.getValue(), message);
                }),
                Case($(instanceOf(DataIntegrityViolationException.class)), ex -> {
                    String message = ex.getMostSpecificCause().getLocalizedMessage();

                    if (message.contains(DUPLICATED_KEY_MESSAGE)) {
                        throw new DaoException(
                                CommonMessageCodes.UNIQUE_CONSTRAINT_VIOLATED.getValue(), message);
                    }

                    throw new DaoException(
                            CommonMessageCodes.DATA_INTEGRITY_CONSTRAINT_VIOLATED.getValue(), message);
                }),
                Case($(instanceOf(InvalidDataAccessApiUsageException.class)), ex -> {
                    String message = ex.getLocalizedMessage();
                    throw new DaoException(
                            CommonMessageCodes.ILLEGAL_SEARCH_PARAMS.getValue(), message);
                }),
                Case($(), ex -> {
                    throw (X) ex;
                })
        );
    }
}
