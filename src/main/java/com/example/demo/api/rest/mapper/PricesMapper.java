package com.example.demo.api.rest.mapper;


import com.example.demo.api.rest.model.PricesDto;
import com.example.demo.domain.entity.Prices;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel= "spring", nullValueCheckStrategy= NullValueCheckStrategy.ALWAYS)
public interface PricesMapper {

    PricesDto mapToDto(Prices prices);

}
