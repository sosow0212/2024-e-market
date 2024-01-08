package com.market.auth.exception;

import com.market.auth.exception.exceptions.ExpiredTokenException;
import com.market.auth.exception.exceptions.LoginInvalidException;
import com.market.auth.exception.exceptions.SignatureInvalidException;
import com.market.auth.exception.exceptions.TokenFormInvalidException;
import com.market.auth.exception.exceptions.TokenInvalidException;
import com.market.auth.exception.exceptions.UnsupportedTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(SignatureInvalidException.class)
    public ResponseEntity<String> handleSignatureInvalidException(final SignatureInvalidException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(TokenFormInvalidException.class)
    public ResponseEntity<String> handleTokenFormInvalidException(final TokenFormInvalidException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<String> handleExpiredTokenException(final ExpiredTokenException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(UnsupportedTokenException.class)
    public ResponseEntity<String> handleUnsupportedTokenException(final UnsupportedTokenException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<String> handleTokenInvalidException(final TokenInvalidException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(LoginInvalidException.class)
    public ResponseEntity<String> handleLoginInvalidException(final LoginInvalidException e) {
        return getUnauthorized(e);
    }

    private ResponseEntity<String> getUnauthorized(final Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }
}
