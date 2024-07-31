package minipj.placepic_core.response;

import lombok.Getter;

@Getter
public enum ErrorCode {
    LOGIN_FAIL(2001, "ID or Password Invalid"),
    UPLOAD_FAIL(1000,"사진업로드에 실패했습니다"),
    MAXIMUM_SIZE_FAIL(1001,"파일 사이즈가 5MB를 초과하였습니다."),
    INVALID_UPLOAD_TYPE(1002,"허용되지 않는 파일유형입니다."),
    UNKNOWN_ERROR(9999, "Unknown Error.");

    private final int code;
    private final String message;


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
