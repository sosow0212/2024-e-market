package batch.server.global.exception.exception;

public class AuthenticationInvalidException extends RuntimeException {

    public AuthenticationInvalidException() {
        super("요청에 대해 인증이 올바르지 않습니다");
    }
}
