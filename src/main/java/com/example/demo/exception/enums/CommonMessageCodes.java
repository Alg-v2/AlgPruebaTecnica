package com.example.demo.exception.enums;

import com.example.demo.messages.MessageResource;

import java.util.HashMap;
import java.util.Map;


/**
 * Códigos de respuesta de error junto con sus equivalentes en códigos http
 */
public enum CommonMessageCodes implements MessageResource {

    MISSING_FIELDS("MISSING_FIELDS", 400),
    TOKEN_MISSING_TENANT("MISSING_TENANT", 400),
    TENANT_DOES_NOT_OWN_ENTITY("TENANT_DOES_NOT_OWN_ENTITY", 400),
    GENERIC_ERROR("GENERIC_ERROR", 500),
    NOT_FOUND("NOT_FOUND", 404),
    ENTITY_NOT_FOUND("ENTITY_NOT_FOUND", 404),
    ENTITY_DOES_NOT_EXISTS("ENTITY_DOES_NOT_EXISTS", 404),
    ILLEGAL_SEARCH_PARAMS("ILLEGAL_SEARCH_PARAMS", 400),
    DATA_INTEGRITY_CONSTRAINT_VIOLATED("DATA_INTEGRITY_CONSTRAINT_VIOLATED", 400),
    UNIQUE_CONSTRAINT_VIOLATED("UNIQUE_CONSTRAINT_VIOLATED", 409),
    NOT_NULL_CONSTRAINT_VIOLATED("NOT_NULL_CONSTRAINT_VIOLATED", 400),
    ENTITY_ALREADY_EXISTS("ENTITY_ALREADY_EXISTS", 409),
    SEARCH_BY_TENANT_NOT_ALLOWED("SEARCH_BY_TENANT_NOT_ALLOWED", 400),
    BAD_REQUEST("BAD_REQUEST", 400),
    RULES_NOT_FONUD("RULES_NOT_FONUD", 404),

    WORKSHOP_API_CONFIG_NOT_SET("WORKSHOP_API_CONFIG_NOT_SET", 500);;

    /**
     * Código de respuesta
     */
    private final String value;

    /**
     * Código http
     */
    private final Integer httpCode;

    CommonMessageCodes(String value, Integer httpCode) {

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

        return httpCode;
    }

    @Override
    public Map<String, Integer> getMessageMap() {

        return MapCodes.MAP;
    }

    private static class MapCodes {

        static final Map<String, Integer> MAP = new HashMap<>();
    }
}
