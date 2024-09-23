package com.example.demo.domain.query;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Builder
@Getter
@ToString
public class SearchPricesToApplyQuery implements Query {

    int product;
    LocalDateTime date;
    int brand;
}


