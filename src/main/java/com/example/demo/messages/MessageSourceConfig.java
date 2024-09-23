package com.example.demo.messages;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * Configuración que permite la búsqueda de ficheros de recursos destinados a mensajes en el framework
 */
@Configuration
public class MessageSourceConfig {

    private static final String MESSAGE_BASE_PATH = "message/";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(searchMessagePropertiesFiles());
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.getDefault());
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    /**
     * Método que busca los ficheros de propiedades con los mensajes que se usarán en las traducciones
     * @return lista de ficheros
     */
    protected String[] searchMessagePropertiesFiles() {
        try {
            final Resource[] resources = new PathMatchingResourcePatternResolver().getResources(
                    ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MESSAGE_BASE_PATH + "*.properties");
            return Arrays.stream(resources).map(Resource::getFilename)
                    .filter(Objects::nonNull)
                    .map(r -> r.replace(".properties", ""))
                    .map(r-> ResourceLoader.CLASSPATH_URL_PREFIX + MESSAGE_BASE_PATH + r).toArray(String[]::new);
        } catch (final IOException e) {
            throw new NotImplementedException(e.getMessage(), e);
        }
    }
}
