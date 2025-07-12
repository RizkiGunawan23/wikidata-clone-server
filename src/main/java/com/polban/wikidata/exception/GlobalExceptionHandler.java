package com.polban.wikidata.exception;

import com.polban.wikidata.dto.response.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(BaseException.class)
        public ResponseEntity<ApiErrorResponse> handleBaseException(BaseException ex) {
                logger.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());

                ApiErrorResponse error = ApiErrorResponse.builder()
                                .message(ex.getMessage())
                                .build();

                return ResponseEntity.status(ex.getStatusCode()).body(error);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
                        HttpMessageNotReadableException ex) {
                logger.warn("Request body missing or malformed: {}", ex.getMessage());

                String message = "Request body diperlukan atau format JSON tidak valid";

                if (ex.getMessage().contains("Required request body is missing")) {
                        message = "Request body diperlukan untuk endpoint ini";
                } else if (ex.getMessage().contains("JSON parse error")) {
                        message = "Format JSON tidak valid";
                }

                ApiErrorResponse error = ApiErrorResponse.builder()
                                .message(message)
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
                Map<String, ArrayList<String>> errors = new HashMap<>();

                // Group errors by field name
                ex.getBindingResult().getFieldErrors().forEach(error -> {
                        String fieldName = error.getField();
                        String errorMessage = error.getDefaultMessage();

                        // Kalau field belum ada di map, buat ArrayList baru
                        if (!errors.containsKey(fieldName)) {
                                errors.put(fieldName, new ArrayList<>());
                        }

                        // Tambahkan error message ke ArrayList field tersebut
                        errors.get(fieldName).add(errorMessage);
                });

                logger.error("Validation error: {}", errors);

                ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                                .message("Data yang dimasukkan tidak valid")
                                .errors(errors)
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
                ApiErrorResponse response = ApiErrorResponse.builder()
                                .message("Endpoint tidak ditemukan: " + ex.getRequestURL())
                                .build();

                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ApiErrorResponse> handleMethodNotSupportedException(
                        HttpRequestMethodNotSupportedException ex) {
                String supportedMethods = String.join(", ", ex.getSupportedMethods());
                ApiErrorResponse response = ApiErrorResponse.builder()
                                .message("Method " + ex.getMethod()
                                                + " tidak didukung untuk endpoint ini. Method yang didukung: "
                                                + supportedMethods)
                                .build();

                return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
                logger.error("Internal server error: ", ex);

                ApiErrorResponse error = ApiErrorResponse.builder()
                                .message("Terjadi kesalahan pada server")
                                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
}
