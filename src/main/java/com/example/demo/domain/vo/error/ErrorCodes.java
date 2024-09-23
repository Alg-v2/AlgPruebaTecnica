package com.example.demo.domain.vo.error;

import com.example.demo.messages.MessageResource;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCodes implements MessageResource {

    DEMO_VALIDATION_ERROR("DEMO_VALIDATION_ERROR", 400),
    INVALID_DATE_RANGE("INVALID_DATE_RANGE", 400),
    BRAND_NOT_FOUND("BRAND_NOT_FOUND", 404),
    PRODUCTO_NOT_FOUND("PRODUCTO_NOT_FOUND", 404);

    private final String value;
    private final Integer httpCode;

    ErrorCodes(
            String value,
            Integer httpCode) {
        this.value = value;
        this.httpCode = httpCode;
        MapCodes.MAP.put(value, httpCode);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getHttpCode() {

        return this.httpCode;
    }

    @Override
    public Map<String, Integer> getMessageMap() {

        return MapCodes.MAP;
    }

    private static class MapCodes {

        static final Map<String, Integer> MAP = new HashMap<>();
    }
}
