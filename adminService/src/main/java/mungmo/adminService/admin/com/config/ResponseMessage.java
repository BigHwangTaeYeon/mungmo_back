package mungmo.adminService.admin.com.config;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResponseMessage {
      OK(200, "Ok", "요청이 성공적으로 되었습니다.")
    , INVALID_PARAMS(400, "InvalidParams", "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.")
    , UNAUTORIZED(401, "Unauthorized", "토큰 정보가 유효하지 않습니다.")
    , UNAVAILABLE(401, "Unavailable", "회원가입이 완료되지 않은 사용자입니다.")
    , FORBIDDEN(403, "Forbidden", "콘텐츠에 접근할 권리를 가지고 있지 않습니다.")
    , VALIDATION(403, "Validation", "올바른 데이터가 아닙니다.")
    , NOT_FOUND(404, "NotFound", "존재하지 않는 데이터입니다.")
    , CONFLICT(409, "Conflict", "데이터가 충돌되었습니다.")
    , PRECONDITIONFAILED(412, "Precondition Failed", "클라이언트의 헤더에 있는 전제조건은 서버의 전제조건에 적절하지 않습니다.")
    , INTERNAL_SERVER_ERROR(500, "InternalServerError", "서버 내부 오류입니다.")
    ;

    ResponseMessage(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private int status;
    private String code;
    private String message;

    public static ResponseMessage valueOfCode(String errorCode) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(errorCode))
                .findAny()
                .orElse(null);
    }
}