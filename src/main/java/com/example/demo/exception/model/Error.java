package com.example.demo.exception.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase usada para tratamiento de errores en las excepciones
 */
@Data
@NoArgsConstructor
public class Error {
    String field;
    String codError;
    private String msgError;

    @Builder
    private Error(String field, String codError, String msgError){
        this.field=field;
        this.codError=codError;
        this.msgError=msgError;
    }

    public Error(String field,String errorCode){

    }
}
