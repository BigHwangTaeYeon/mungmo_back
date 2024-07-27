package mungMo.boardService.com.response.exception;

public class NotFoundException extends RuntimeException {
    // 생성자에서 상위 클래스의 생성자를 호출하여
    // 예외 메시지를 설정합니다.
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException() {
        super("존재하지 않는 데이터입니다.");
    }
}
