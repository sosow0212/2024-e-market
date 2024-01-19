package com.market.comment.exception;

import com.market.comment.exception.exceptions.CommentNotFoundException;
import com.market.comment.exception.exceptions.CommentWriterNotEqualsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(final CommentNotFoundException e) {
        return getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(CommentWriterNotEqualsException.class)
    public ResponseEntity<String> handleCommentWriterNotEqualsException(final CommentWriterNotEqualsException e) {
        return getResponse(HttpStatus.UNAUTHORIZED, e);
    }

    private ResponseEntity<String> getResponse(final HttpStatus httpStatus, final RuntimeException e) {
        return ResponseEntity.status(httpStatus)
                .body(e.getMessage());
    }
}
