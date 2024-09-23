package com.example.demo.application.process;

import com.example.demo.domain.entity.Prices;
import com.example.demo.domain.query.QueryFailure;
import com.example.demo.domain.query.SearchPricesToApplyQuery;
import io.vavr.control.Either;

public interface QueryPricesProcess {

    Either<QueryFailure, Prices> searchPricesToApply(SearchPricesToApplyQuery query);
}
