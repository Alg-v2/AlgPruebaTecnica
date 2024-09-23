package com.example.demo.domain.query.validator;

import com.example.demo.exception.model.Error;
import com.example.demo.domain.entity.Prices;
import com.example.demo.domain.query.QueryFailure;
import com.example.demo.domain.query.QueryValidator;
import com.example.demo.domain.query.SearchPricesToApplyQuery;
import com.example.demo.domain.repository.PricesReadOnlyRepository;
import com.example.demo.domain.vo.error.ErrorCodes;
import com.example.demo.messages.MessageResolver;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class QuerySearchPricesValidator implements QueryValidator<SearchPricesToApplyQuery> {
    private final PricesReadOnlyRepository pricesReadOnlyRepository;

    @Override
    public Either<QueryFailure, SearchPricesToApplyQuery> acceptOrReject(SearchPricesToApplyQuery query) {

        ArrayList<Error> errors=new ArrayList<>();

        List<Prices> pricesList=pricesReadOnlyRepository.findByProductId(query.getProduct());

        if(pricesList.isEmpty()){

            errors.add(Error.builder().field("id")
                            .msgError(MessageResolver.getMessage(ErrorCodes.PRODUCTO_NOT_FOUND.getValue()))
                            .codError(ErrorCodes.PRODUCTO_NOT_FOUND.name())
                    .build());

        }
        pricesList=pricesReadOnlyRepository.findByBrandId(query.getBrand());


        if(pricesList.isEmpty()){

            errors.add(Error.builder().field("id")
                    .msgError(MessageResolver.getMessage(ErrorCodes.BRAND_NOT_FOUND.getValue()))
                    .codError(ErrorCodes.BRAND_NOT_FOUND.name())
                    .build());

        }

        pricesList=pricesReadOnlyRepository.findByDate(query.getDate());

        if(pricesList.isEmpty()){

            errors.add(Error.builder().field("id")
                    .msgError(MessageResolver.getMessage(ErrorCodes.INVALID_DATE_RANGE.getValue()))
                    .codError(ErrorCodes.INVALID_DATE_RANGE.name())
                    .build());

        }

        return errors.isEmpty() ? Either.right(query) : Either.left(QueryFailure.builder().code(ErrorCodes.DEMO_VALIDATION_ERROR.name()).errors(errors).build());

    }
}
