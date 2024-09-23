package com.example.demo.exception.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase usada en las excepciones para parsear la respuesta http con las opciones estándar
 */
@Getter
@Setter
public class ApiErrorResponse extends ErrorResponse {

    public ApiErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, List<Error> errors) {
        super(timestamp, error, message, errors);
        this.status = status;
        this.path = path;
    }

    /**
     * Código del error http
     */
    private int status;

    /**
     * Endpoint que ha provocado el error
     */
    private String path;

}
