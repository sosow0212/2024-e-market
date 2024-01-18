package com.market.board.exception.exceptions;

public class WriterNotEqualsException extends RuntimeException {

    public WriterNotEqualsException() {
        super("글쓴이가 일치하지 않습니다.");
    }
}
