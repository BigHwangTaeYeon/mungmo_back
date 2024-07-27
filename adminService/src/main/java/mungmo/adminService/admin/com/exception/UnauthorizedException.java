package mungmo.adminService.admin.com.exception;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
    }
    public UnauthorizedException(String message) {
        super(message);
    }
}
