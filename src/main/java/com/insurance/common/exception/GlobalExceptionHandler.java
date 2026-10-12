package com.insurance.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException exception, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(HttpStatus.NOT_FOUND, "NOT_FOUND",
                List.of(exception.getMessage()), request));
    }

    @ExceptionHandler({DuplicateException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateException(final DuplicateException exception, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildErrorResponse(HttpStatus.CONFLICT, "DUPLICATE",
                List.of(exception.getMessage()), request));
    }

    @ExceptionHandler({InvalidXmlException.class})
    public ResponseEntity<ErrorResponse> handleInvalidXmlException(final InvalidXmlException exception, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_XML",
                List.of(exception.getMessage()), request));
    }

    @ExceptionHandler({XmlSchemaException.class})
    public ResponseEntity<ErrorResponse> handleXmlSchemaException(final XmlSchemaException exception, final HttpServletRequest request) {
        log.error("XML schema processing failed", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_XML_SCHEMA",
                List.of("Failed to process XML schema"), request));
    }

    @ExceptionHandler({XmlMarshallingException.class})
    public ResponseEntity<ErrorResponse> handleXmlMarshallingException(final XmlMarshallingException exception, final HttpServletRequest request) {
        log.error("XML marshalling failed", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_XML_MARSHALLING",
                List.of("Failed XML marshalling"), request));
    }

    @ExceptionHandler({InvalidJsonException.class})
    public ResponseEntity<ErrorResponse> handleInvalidJsonException(final InvalidJsonException exception, final HttpServletRequest request) {
        log.error("Invalid JSON exception", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_JSON",
                List.of("Invalid JSON exception"), request));
    }

    @ExceptionHandler({JsonSerializationException.class})
    public ResponseEntity<ErrorResponse> handleJsonSerializationException(final JsonSerializationException exception, final HttpServletRequest request) {
        log.error("Invalid JSON serialization", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_JSON_SERIALIZATION",
                List.of("Invalid JSON serialization"), request));
    }

    @ExceptionHandler({RequestMismatchException.class})
    public ResponseEntity<ErrorResponse> handleRequestMismatchException(final RequestMismatchException exception, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, "REQUEST_MISMATCH",
                List.of(exception.getMessage()), request));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException exception, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, "CONSTRAINT_VIOLATION",
                exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()), request));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED",
                exception.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()), request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception exception, final HttpServletRequest request) {
        log.error("Unexpected error", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                        List.of("Unexpected server error"), request));
    }

    private ErrorResponse buildErrorResponse(
            HttpStatus status,
            String error,
            List<String> messages,
            HttpServletRequest request) {

        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(error)
                .messages(messages)
                .path(request.getRequestURI())
                .build();
    }
}
