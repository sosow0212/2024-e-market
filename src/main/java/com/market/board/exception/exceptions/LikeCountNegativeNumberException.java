package com.market.board.exception.exceptions;

public class LikeCountNegativeNumberException extends RuntimeException {

    public LikeCountNegativeNumberException() {
        super("좋아요 수는 음수가 될 수 없습니다.");
    }
}
