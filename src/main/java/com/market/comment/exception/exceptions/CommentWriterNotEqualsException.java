package com.market.comment.exception.exceptions;

public class CommentWriterNotEqualsException extends RuntimeException {

    public CommentWriterNotEqualsException() {
        super("글쓴이가 일치하지 않습니다.");
    }
}
