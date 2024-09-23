package com.example.demo.exception;

import cz.jirutka.rsql.parser.RSQLParserException;
import com.example.demo.exception.model.ApiErrorResponse;
import com.example.demo.exception.model.Error;
import com.example.demo.messages.MessageResource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class HttpResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INVALID_REQUEST = "Argumento de petición no válido";
    private Map<String, Integer> responseCodes = new HashMap<>();

    public HttpResponseEntityExceptionHandler() {

        setMessageResolvers();
    }

    /**
     * Busca en el framework todas las clases que implementan
     * {@link MessageResource} y crea un mapa de código de error - código http
     */
    @SuppressWarnings("unchecked")
    public void setMessageResolvers() {

        final List<Class<?>> result = new LinkedList<>();
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false, new StandardServletEnvironment());

        provider.addIncludeFilter(new AssignableTypeFilter(MessageResource.class));
        for (BeanDefinition beanDefinition : provider.findCandidateComponents("es.facsa.waternology")) {
            try {
                result.add(Class.forName(beanDefinition.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                log.warn("Could not resolve class object for bean definition", e);
            }
        }

        Class<MessageResource>[] allAnnotatedClassesInPackage = (Class<MessageResource>[]) result
                .toArray(new Class<?>[0]);
        Arrays.stream(allAnnotatedClassesInPackage).forEach(s -> {
            MessageResource[] enumConstants = s.getEnumConstants();
            Arrays.stream(enumConstants).forEach(c -> this.responseCodes.putAll(c.getMessageMap()));
        });
    }

    /**
     * Evaluamos el código de la excepción, en caso de no encontrarse, se envía un
     * 500
     *
     * @param exception exceptión
     * @return httpStatus
     */
    protected HttpStatus evaluateHttpCode(final AbstractRestException exception) {

        return responseCodes.get(exception.getCode()) != null
                ? HttpStatus.valueOf(responseCodes.get(exception.getCode()))
                : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Genera el ApiErrorResponse
     *
     * @param ex         exception
     * @param request    request
     * @param httpStatus httpStatus
     * @return ApiErrorResponse
     */
    protected ApiErrorResponse handleErrorResponse(final AbstractRestException ex, final HttpServletRequest request,
                                                   final HttpStatus httpStatus) {

        return handleErrorResponse(ex, request, httpStatus, ex.getMessage(), ex.getErrors());
    }

    protected ApiErrorResponse handleErrorResponse(Exception ex, HttpServletRequest request, HttpStatus httpStatus,
                                                   String message, List<Error> errorList) {

        errorList.stream()
                .filter(error -> !StringUtils.isBlank(error.getMsgError()))
                .forEach(error -> error.setMsgError(error.getMsgError().replace('"', '\'')));

        return new ApiErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message != null ? message.replace('"', '\'') : null,
                request.getRequestURI(),
                errorList
        );
    }

    /**
     * Manejador de las excepciones descendientes de {@link BaseException}
     *
     * @param ex      excepción
     * @param request httpRequest
     * @return respuesta
     */
    @ExceptionHandler(value = BaseException.class)
    protected ResponseEntity<ApiErrorResponse> handleBaseCommonExceptions(final BaseException ex,
                                                                          final HttpServletRequest request) {

        log.error("Code: {} , Message: {}", ex.getCode(), ex.getMessage());
        final HttpStatus httpStatus = evaluateHttpCode(ex);
        ApiErrorResponse errorResponse = handleErrorResponse(ex, request, httpStatus);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    /**
     * Handler para cuando se hacen peticiones RSQL erróneas.
     *
     * @param ex      excepción {@link RSQLParserException}
     * @param request request http
     * @return ResponseEntity
     */
    @ExceptionHandler(value = RSQLParserException.class)
    protected ResponseEntity<ApiErrorResponse> handleRSQLParserException(final RSQLParserException ex,
                                                                         final HttpServletRequest request) {

        log.error("Message: {}", ex.getLocalizedMessage());
        final ApiErrorResponse errorResponse = handleErrorResponse(ex, request, HttpStatus.BAD_REQUEST, INVALID_REQUEST,
                List.of(Error.builder()
                        .codError(HttpStatus.BAD_REQUEST.name())
                        .msgError(ex.getLocalizedMessage())
                        .build()));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        log.error("Code: {} , Message: {}", ex.getErrorCode(), ex.getMessage());
        final ApiErrorResponse errorResponse = handleErrorResponse(ex, request, HttpStatus.BAD_REQUEST, INVALID_REQUEST,
                List.of(Error.builder()
                        .codError(ex.getErrorCode())
                        .field(ex.getName())
                        .msgError(ex.getLocalizedMessage())
                        .build()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Handler que se ejecuta cuando fallan las validaciones mediante JSR del dto de entrada al acceder al endpoint
     *
     * @param ex      excepción producida
     * @param headers headers de la llamada http
     * @param status  status de la llamada http
     * @param request request http
     * @return ResponseEntity
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        final String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        final String message = INVALID_REQUEST;

        log.info(message, ex);

        final List<Error> errorList = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(fieldError -> {
            try {
                Field field = fieldError.getClass().getDeclaredField("field");
                field.setAccessible(true);
                String fieldValue = (String) field.get(fieldError);

                Error error = new Error(fieldValue, fieldError.getCode());
                error.setMsgError(fieldError.getDefaultMessage());
                errorList.add(error);

            } catch (final Exception e) {
                log.error(e.getMessage(), e);
            }
        });
        final ApiErrorResponse errorResponse =
                handleErrorResponse(ex, ((ServletWebRequest) request).getRequest(), HttpStatus.BAD_REQUEST, message, errorList);
        return new ResponseEntity<>(errorResponse, status);

    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {

        final String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        final String message = INVALID_REQUEST;

        log.info(message, ex);

        final List<Error> errorList = new ArrayList<>();
        String fieldValue = null;
        try {
            Field field = ex.getClass().getDeclaredField("name");
            field.setAccessible(true);
            fieldValue = (String) field.get(ex);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Error error = new Error(fieldValue, ex.getErrorCode());
        error.setMsgError(ex.getLocalizedMessage());
        errorList.add(error);

        final ApiErrorResponse errorResponse =
                handleErrorResponse(ex, ((ServletWebRequest) request).getRequest(), HttpStatus.BAD_REQUEST, message, errorList);
        return new ResponseEntity<>(errorResponse, status);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        final String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        final String message = ex.getMessage();

        log.info(message, ex);

        final List<Error> errorList = new ArrayList<>();

        final ApiErrorResponse errorResponse =
                handleErrorResponse(ex, ((ServletWebRequest) request).getRequest(), HttpStatus.BAD_REQUEST, message, errorList);

        return new ResponseEntity<>(errorResponse, status);
    }

}