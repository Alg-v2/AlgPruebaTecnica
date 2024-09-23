package com.example.demo.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase usada para parsear la respuesta de errores en las excepciones
 */
@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {

    LocalDateTime timestamp;
    String error;
    String message;
    List<Error> errors;

    public ErrorResponse(LocalDateTime timestamp, String error, String message) {
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
    }
}
