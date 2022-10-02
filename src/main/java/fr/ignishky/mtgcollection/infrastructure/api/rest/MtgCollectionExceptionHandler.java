package fr.ignishky.mtgcollection.infrastructure.api.rest;

import fr.ignishky.mtgcollection.domain.block.exception.BlockNotFoundException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class MtgCollectionExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = getLogger(MtgCollectionExceptionHandler.class);

    @ExceptionHandler(BlockNotFoundException.class)
    protected static ResponseEntity<Object> handleNotFound(Exception ex, @NonNull WebRequest request) {

        logWarning(request, NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(NOT_FOUND);
    }

    private static void logWarning(WebRequest request, HttpStatus httpStatus, String errorDetails) {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) request).getRequest();
        LOGGER.warn("Answered call %s %s with status %s : %s".formatted(
                servletRequest.getMethod(),
                servletRequest.getRequestURI(),
                httpStatus.value(),
                errorDetails
        ));
    }

}
