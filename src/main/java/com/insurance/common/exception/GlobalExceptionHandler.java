package com.insurance.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({DuplicateException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateException(final DuplicateException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({InvalidXmlException.class})
    public ResponseEntity<ErrorResponse> handleInvalidXmlException(final InvalidXmlException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({XmlSchemaException.class})
    public ResponseEntity<ErrorResponse> handleXmlSchemaException(final XmlSchemaException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({XmlMarshallingException.class})
    public ResponseEntity<ErrorResponse> handleXmlSchemaException(final XmlMarshallingException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({InvalidJsonException.class})
    public ResponseEntity<ErrorResponse> handleInvalidJsonException(final InvalidJsonException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({JsonSerializationException.class})
    public ResponseEntity<ErrorResponse> handleJsonSerializationException(final JsonSerializationException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), List.of(exception.getMessage()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()), exception.getClass().getSimpleName()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()), exception.getClass().getSimpleName()));
    }
}
