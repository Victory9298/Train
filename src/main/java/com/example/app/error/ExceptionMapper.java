package com.example.app.error;

import com.example.app.error.exception.BusinessException;
import com.example.app.error.exception.TechnicalException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionMapper {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(ExceptionMapper.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException e) {
        log.error(e.getMessage(), e);
        kafkaTemplate.send("topic1", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = TechnicalException.class)
    protected ResponseEntity<com.example.app.error.ErrorResponse> handleTechnicalException(
            TechnicalException e) {
        log.error("Internal technical error", e);
        kafkaTemplate.send("topic1", "Internal technical error" + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new InternalErrorResponse());
    }

}
