package com.example.demo.messages;

import java.util.Map;

/**
 * Interfaz que deberán establecer todas las clases que tengan enumerados asociados con mensajes para que el framework
 * pueda tenerlos en cuenta
 */
public interface MessageResource {

    String getValue();

    Integer getHttpCode();

    Map<String, Integer> getMessageMap();
}