package com.example.demo.application.process;

import com.example.demo.domain.entity.Prices;
import com.example.demo.domain.query.QueryFailure;
import com.example.demo.domain.query.SearchPricesToApplyQuery;
import com.example.demo.domain.query.handler.SearchPricesToApplyHandler;
import com.example.demo.exception.CommonExceptionHandler;
import com.example.demo.exception.DomainException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class QueryPricesProcessImpl implements QueryPricesProcess {

    SearchPricesToApplyHandler searchPricesToApplyHandler;

    @Override
    public Either<QueryFailure, Prices> searchPricesToApply(SearchPricesToApplyQuery query){
        return Try.of(() -> searchPricesToApplyHandler.handle(query))
                .getOrElseThrow(CommonExceptionHandler::handleException)
                .fold(reject ->{
                    throw new DomainException(reject);
                    },Either::right);
    }
}
