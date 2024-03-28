package com.server.community.exception;

import com.server.community.exception.exceptions.BoardNotFoundException;
import com.server.community.exception.exceptions.FileUploadFailureException;
import com.server.community.exception.exceptions.LikeCountNegativeNumberException;
import com.server.community.exception.exceptions.UnsupportedImageFormatException;
import com.server.community.exception.exceptions.WriterNotEqualsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> handleBoardNotFoundException(final BoardNotFoundException e) {
        return getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(WriterNotEqualsException.class)
    public ResponseEntity<String> handleWriterNotEqualsException(final WriterNotEqualsException e) {
        return getResponse(HttpStatus.UNAUTHORIZED, e);
    }

    @ExceptionHandler(UnsupportedImageFormatException.class)
    public ResponseEntity<String> handleUnsupportedImageFormatException(final UnsupportedImageFormatException e) {
        return getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(FileUploadFailureException.class)
    public ResponseEntity<String> handleFileUploadFailureException(final FileUploadFailureException e) {
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(LikeCountNegativeNumberException.class)
    public ResponseEntity<String> handleLikeCountNegativeNumberException(final LikeCountNegativeNumberException e) {
        return getResponse(HttpStatus.BAD_REQUEST, e);
    }

    private ResponseEntity<String> getResponse(final HttpStatus httpStatus, final RuntimeException e) {
        return ResponseEntity.status(httpStatus)
                .body(e.getMessage());
    }
}
