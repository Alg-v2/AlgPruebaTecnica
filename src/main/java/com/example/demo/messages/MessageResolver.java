package com.example.demo.messages;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

/**
 * Clase que permite la resolución de mensajes y la traducción de los mismos
 */
public class MessageResolver {

    private MessageResolver() {
    }

    private static final MessageSource MESSAGE_SOURCE = MessageSourceProvider.getMessageSource();

    /**
     * Resuelve el mensaje dado un código y sus argumentos
     *
     * @param code código
     * @param args argumentos
     * @return mensaje
     */
    public static String getMessage(final String code, final Object... args) {
        return getMessage(code, null, args);
    }

    /**
     * Resuelve el mensaje dado un código
     *
     * @param code código
     * @return mensaje
     */
    public static String getMessage(final String code) {
        return getMessage(code, null, new Object[0]);
    }

    /**
     * Resuelve el mensaje dado un código y un locale
     *
     * @param code   código
     * @param locale locale
     * @return mensaje
     */
    public static String getMessage(final String code, final Locale locale) {
        return getMessage(code, locale, new Object[0]);
    }

    /**
     * Resuelve el mensaje dado un código, locale y argumentos
     *
     * @param code   código
     * @param locale locale
     * @param args   argumentos
     * @return mensaje
     */
    public static String getMessage(final String code, final Locale locale, final Object... args) {
        Locale localeFinal = locale;

        if (localeFinal == null) {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = sra.getRequest();
            localeFinal = request.getLocale();
        }

        if (localeFinal == null) {
            localeFinal = LocaleContextHolder.getLocale();
        }
        return MESSAGE_SOURCE.getMessage(code, args, localeFinal);
}

}
