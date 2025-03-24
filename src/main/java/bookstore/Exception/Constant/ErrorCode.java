package bookstore.Exception.Constant;

public class ErrorCode {
    private ErrorCode() {
        throw new IllegalStateException("ErrorCode class");
    }

    public static final Long CODE_ERROR = 0L;
    public static final Long CODE_SUCCESS = 1L;
}
