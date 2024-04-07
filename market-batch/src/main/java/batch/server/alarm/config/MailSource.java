package batch.server.alarm.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MailSource {

    TITLE("E-market 회원가입 성공 안내"),
    CONTENT("""
                    <h1> E-market </h1>
                    <br>
                    <p>E-market 회원가입에 성공하셨습니다.<p>
                    <br>
                    <p>해당 이메일은 회원가입 성공 안내 메시지입니다.<p>
                    <br>
                    <p>회원가입 기념 쿠폰을 발급하였습니다.<p>
                    <br>
            """);

    private final String message;

    public static String getMailMessage(final Long id, final String nickname) {
        String message = "";

        message += TITLE.message;
        message += "<div style='margin:100px;'>";
        message += "<p>" + id + "번 유저인" + nickname + "님 환영합니다.<p>";
        message += CONTENT.message;
        message += "</div>";

        return message;
    }
}
