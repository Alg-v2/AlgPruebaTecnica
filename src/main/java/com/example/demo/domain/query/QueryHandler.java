package com.example.demo.domain.query;

import io.vavr.control.Either;

public interface QueryHandler<Q extends Query,R> {
    Either<QueryFailure,R> handle(Q query);
}
