package com.market.market.exception;

import com.market.market.exception.exceptions.ProductNotFoundException;
import com.market.market.exception.exceptions.ProductOwnerNotEqualsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MarketExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handlerProductNotFoundException(final ProductNotFoundException e) {
        return getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(ProductOwnerNotEqualsException.class)
    public ResponseEntity<String> handlerProductOwnerNotEqualsException(final ProductOwnerNotEqualsException e) {
        return getResponse(HttpStatus.UNAUTHORIZED, e);
    }

    private ResponseEntity<String> getResponse(final HttpStatus httpStatus, final RuntimeException e) {
        return ResponseEntity.status(httpStatus)
                .body(e.getMessage());
    }
}
