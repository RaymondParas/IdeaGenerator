package com.IdeaGenerator.IdeaGenerator.exceptionHandle;

import com.IdeaGenerator.IdeaGenerator.models.CustomExceptionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleRequest(RequestException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomExceptionModel customException = new CustomExceptionModel(
                exception.getMessage(),
                status,
                ZonedDateTime.now(),
                exception
        );

        log.warn(exception.getMessage());

        return new ResponseEntity<>(customException, status);
    }

    @ExceptionHandler(value = {RecordNotFoundException.class})
    public ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomExceptionModel customException = new CustomExceptionModel(
                exception.getMessage(),
                status,
                ZonedDateTime.now(),
                exception
        );

        log.warn(exception.getMessage());

        return new ResponseEntity<>(customException, status);
    }
}
