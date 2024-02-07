package com.market.coupon.exception;

import com.market.coupon.exception.exceptions.CouponAmountRangeInvalidException;
import com.market.coupon.exception.exceptions.CouponNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponExceptionHandler {

    @ExceptionHandler(CouponAmountRangeInvalidException.class)
    public ResponseEntity<String> handleCouponAmountRangeInvalidException(final CouponAmountRangeInvalidException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<String> handleCouponNotFoundException(final CouponNotFoundException e) {
        return getResponse(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> getResponse(final RuntimeException e, final HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(e.getMessage());
    }
}
