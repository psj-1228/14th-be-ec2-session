package project.ec2session.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 로그인 과정 에러.
    USER_INVALID_PASSWORD(404, "잘못된 비밀번호입니다."),
    INVALID_USER_INFO(404, "정보를 정확히 입력해주세요."),

    // 존재하지 않는 값을 보낼 때.
    USER_NOT_FOUND(404, "존재하지 않는 회원입니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR(500, "서버 에러입니다. 서버 팀에 연락주세요.");

    private final int status;
    private final String message;
}
