package com.example.demo.domain.query.handler;

import com.example.demo.domain.entity.Prices;
import com.example.demo.domain.query.QueryFailure;
import com.example.demo.domain.query.QueryHandler;
import com.example.demo.domain.query.SearchPricesToApplyQuery;
import com.example.demo.domain.query.validator.QuerySearchPricesValidator;
import com.example.demo.domain.repository.PricesReadOnlyRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchPricesToApplyHandler implements QueryHandler<SearchPricesToApplyQuery, Prices> {

    private final QuerySearchPricesValidator querySearchPricesValidator;
    private final PricesReadOnlyRepository pricesReadOnlyRepository;

    @Override
    public Either<QueryFailure, Prices> handle(SearchPricesToApplyQuery query) {
        return querySearchPricesValidator.acceptOrReject(query)
                .fold(
                        Either::left,
                        accept -> {
                            Prices price = new Prices();
                            Optional<Prices> priceOptional = pricesReadOnlyRepository.findByProductAndDateAndBand(query.getProduct(),query.getDate(), query.getBrand());
                            if(priceOptional.isPresent()){
                                 price = priceOptional.get();
                            }
                            return Either.right(price);

                        });

    }

}
