package com.example.demo.prices;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.demo.domain.entity.Prices;
import com.example.demo.domain.query.SearchPricesToApplyQuery;
import com.example.demo.domain.query.handler.SearchPricesToApplyHandler;
import com.example.demo.domain.query.validator.QuerySearchPricesValidator;
import com.example.demo.domain.repository.PricesReadOnlyRepository;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDateTime;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchPricesToApplyHandlerTest {
    @Mock
    private QuerySearchPricesValidator querySearchPricesValidator;

    @Mock
    private PricesReadOnlyRepository pricesReadOnlyRepository;

    private SearchPricesToApplyHandler searchPricesToApplyHandler;

    @BeforeAll
    void setUp(){
        MockitoAnnotations.openMocks(this);

        searchPricesToApplyHandler = new SearchPricesToApplyHandler(querySearchPricesValidator,pricesReadOnlyRepository);
    }

    @Test
    void searchPricesToApplyHandlerTest1(){
        SearchPricesToApplyQuery searchPricesToApplyQuery = SearchPricesToApplyQuery.builder().product(35455).date(LocalDateTime.of(2020,6,14,10,00,0,0)).brand(1).build();

        Prices prices = Prices.builder().productId(35455).startDate(LocalDateTime.of(2020,6,14,00,00,0,0)).endDate(LocalDateTime.of(2020,12,31,23,59,59,0)).brandId(1).build();

    when(querySearchPricesValidator.acceptOrReject(any())).thenReturn(Either.right(searchPricesToApplyQuery));
    when(pricesReadOnlyRepository.findByProductAndDateAndBand(searchPricesToApplyQuery.getProduct(),searchPricesToApplyQuery.getDate(),searchPricesToApplyQuery.getBrand())).thenReturn(Optional.of(prices));

    var handle= searchPricesToApplyHandler.handle(searchPricesToApplyQuery);

    assertTrue(handle.isRight());
    }

    @Test
    void searchPricesToApplyHandlerTest2(){
        SearchPricesToApplyQuery searchPricesToApplyQuery = SearchPricesToApplyQuery.builder().product(35455).date(LocalDateTime.of(2020,6,14,16,00,0,0)).brand(1).build();

        Prices prices = Prices.builder().productId(35455).startDate(LocalDateTime.of(2020,6,14,15,00,0,0)).endDate(LocalDateTime.of(2020,6,14,18,30,0,0)).brandId(1).build();

        when(querySearchPricesValidator.acceptOrReject(any())).thenReturn(Either.right(searchPricesToApplyQuery));
        when(pricesReadOnlyRepository.findByProductAndDateAndBand(searchPricesToApplyQuery.getProduct(),searchPricesToApplyQuery.getDate(),searchPricesToApplyQuery.getBrand())).thenReturn(Optional.of(prices));

        var handle= searchPricesToApplyHandler.handle(searchPricesToApplyQuery);

        assertTrue(handle.isRight());
    }

    @Test
    void searchPricesToApplyHandlerTest3(){
        SearchPricesToApplyQuery searchPricesToApplyQuery = SearchPricesToApplyQuery.builder().product(35455).date(LocalDateTime.of(2020,6,14,21,00,0,0)).brand(1).build();

        Prices prices = Prices.builder().productId(35455).startDate(LocalDateTime.of(2020,6,14,00,00,0,0)).endDate(LocalDateTime.of(2020,12,31,23,59,59,0)).brandId(1).build();

        when(querySearchPricesValidator.acceptOrReject(any())).thenReturn(Either.right(searchPricesToApplyQuery));
        when(pricesReadOnlyRepository.findByProductAndDateAndBand(searchPricesToApplyQuery.getProduct(),searchPricesToApplyQuery.getDate(),searchPricesToApplyQuery.getBrand())).thenReturn(Optional.of(prices));

        var handle= searchPricesToApplyHandler.handle(searchPricesToApplyQuery);

        assertTrue(handle.isRight());
    }

    @Test
    void searchPricesToApplyHandlerTest4(){
        SearchPricesToApplyQuery searchPricesToApplyQuery = SearchPricesToApplyQuery.builder().product(35455).date(LocalDateTime.of(2020,6,15,10,00,0,0)).brand(1).build();

        Prices prices = Prices.builder().productId(35455).startDate(LocalDateTime.of(2020,6,15,00,00,0,0)).endDate(LocalDateTime.of(2020,6,15,11,00,0,0)).brandId(1).build();

        when(querySearchPricesValidator.acceptOrReject(any())).thenReturn(Either.right(searchPricesToApplyQuery));
        when(pricesReadOnlyRepository.findByProductAndDateAndBand(searchPricesToApplyQuery.getProduct(),searchPricesToApplyQuery.getDate(),searchPricesToApplyQuery.getBrand())).thenReturn(Optional.of(prices));

        var handle= searchPricesToApplyHandler.handle(searchPricesToApplyQuery);

        assertTrue(handle.isRight());
    }

    @Test
    void searchPricesToApplyHandlerTest5(){
        SearchPricesToApplyQuery searchPricesToApplyQuery = SearchPricesToApplyQuery.builder().product(35455).date(LocalDateTime.of(2020,6,16,21,00,0,0)).brand(1).build();

        Prices prices = Prices.builder().productId(35455).startDate(LocalDateTime.of(2020,6,15,16,00,0,0)).endDate(LocalDateTime.of(2020,12,31,23,59,59,0)).brandId(1).build();

        when(querySearchPricesValidator.acceptOrReject(any())).thenReturn(Either.right(searchPricesToApplyQuery));
        when(pricesReadOnlyRepository.findByProductAndDateAndBand(searchPricesToApplyQuery.getProduct(),searchPricesToApplyQuery.getDate(),searchPricesToApplyQuery.getBrand())).thenReturn(Optional.of(prices));

        var handle= searchPricesToApplyHandler.handle(searchPricesToApplyQuery);

        assertTrue(handle.isRight());
    }
}
