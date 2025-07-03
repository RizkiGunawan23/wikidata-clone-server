package com.polban.wikidata.exception;

import com.polban.wikidata.dto.response.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
                                .statusCode(ex.getStatusCode())
                                .message(ex.getMessage())
                                .build();

                return ResponseEntity.status(ex.getStatusCode()).body(error);
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
                                .statusCode(HttpStatus.BAD_REQUEST)
                                .message("Data yang dimasukkan tidak valid")
                                .errors(errors)
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex) {
                logger.error("Internal server error: ", ex);

                ApiErrorResponse error = ApiErrorResponse.builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                                .message("Terjadi kesalahan pada server")
                                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
}
