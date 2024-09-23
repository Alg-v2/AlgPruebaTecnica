package com.example.demo.domain.query;

import io.vavr.control.Either;

public interface QueryValidator<C extends Query> {
Either<QueryFailure, C> acceptOrReject(C query);
}
