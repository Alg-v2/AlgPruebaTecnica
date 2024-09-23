package com.example.demo.api.rest.controller;

import com.example.demo.api.rest.services.PricesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.api.rest.model.*;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class V1ApiDelegateImpl implements V1ApiDelegate {

    PricesService pricesService;

    @Override
    public ResponseEntity<PricesDto> searchPricesToApply(Integer product, LocalDateTime date,Integer brand){
        PricesDto res= pricesService.searchPricesToApply(product,date,brand);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
