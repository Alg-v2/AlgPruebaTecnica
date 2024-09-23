package com.example.demo.api.rest.services;

import com.example.demo.api.rest.mapper.PricesMapper;
import com.example.demo.api.rest.model.PricesDto;
import com.example.demo.application.process.QueryPricesProcess;
import com.example.demo.domain.entity.Prices;
import com.example.demo.domain.query.QueryFailure;
import com.example.demo.domain.query.SearchPricesToApplyQuery;
import com.example.demo.exception.DomainException;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PricesService {

    PricesMapper pricesMapper;
    QueryPricesProcess queryPricesProcess;

    public PricesDto searchPricesToApply(int product, LocalDateTime date, int brand){

        SearchPricesToApplyQuery performQuery = SearchPricesToApplyQuery.builder().product(product).date(date).brand(brand).build();
        Either<QueryFailure, Prices> either = queryPricesProcess.searchPricesToApply(performQuery);

        if(either.isLeft()){
            throw new DomainException(either.getLeft());
        }

        return pricesMapper.mapToDto(either.get());

    }
}
