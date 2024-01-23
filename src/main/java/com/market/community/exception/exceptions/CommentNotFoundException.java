package com.market.community.exception.exceptions;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException() {
        super("댓글을 찾을 수 없습니다.");
    }
}
