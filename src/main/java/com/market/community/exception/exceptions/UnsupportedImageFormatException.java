package com.market.community.exception.exceptions;

public class UnsupportedImageFormatException extends RuntimeException {

    public UnsupportedImageFormatException() {
        super("이미지 확장자 형식이 맞지 않습니다. jpg, jpeg, gif, bmp, png 확장자로 변경 후 재시도 해주세요.");
    }
}
