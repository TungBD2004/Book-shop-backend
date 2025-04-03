package bookstore.Exception.Constant;

public class ErrorMessage {
    public ErrorMessage() {
    }

    public static class User{
        public static final String SUCCESS = "Success";
        public static final String USER_NOT_FOUND = "Không tìm thấy người dùng";
        public static final String PERMISSION_DENIED = "Không có quyền truy cập!";
        public static final String EMAIL_OR_PASSWORD_INCORRECT = "Email hoặc mật khẩu không chính xác!";
        public static final String USER_ALREADY_EXISTS = "Email đã tồn tại";
        public static final String USER_NOT_EXISTS = "Email không tồn tại";
        public static final String RE_PASSWORD_NOT_MATCH = "Mật khẩu nhập lại không chính xác";
        public User() {
        }
    }
}
