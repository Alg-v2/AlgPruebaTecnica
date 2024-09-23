package com.example.demo.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/**
 * Implementación de {@link MessageSourceAware} para la gestión de recursos de mensajes
 */
@Component
public class MessageSourceProvider implements MessageSourceAware {

    private static MessageSource messageSource;

    public static synchronized MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * This only works if spring.main.lazyInitialization is false
     *
     * @param messageSource messageSource
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        // Due to static messageSource property
        synchronized (this) {
            MessageSourceProvider.messageSource = messageSource;
        }
    }
}