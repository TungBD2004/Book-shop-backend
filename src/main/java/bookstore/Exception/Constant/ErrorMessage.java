package bookstore.Exception.Constant;

public class ErrorMessage {
    public ErrorMessage() {
    }

    public static class User{
        public static final String USER_NOT_FOUND = "Không tìm thấy người dùng!";
        public static final String PERMISSION_DENIED = "Không có quyền truy cập!";
        public static final String EMAIL_OR_PASSWORD_INCORRECT = "Email hoặc mật khẩu không chính xác!";
        public static final String USER_ALREADY_EXISTS = "Email đã tồn tại!";
        public static final String EMAIL_INVALID = "Email không hợp lệ";
        public static final String USER_NOT_EXISTS = "Email không tồn tại!";
        public static final String RE_PASSWORD_NOT_MATCH = "Mật khẩu nhập lại không chính xác!";
        public static final String UPDATE_USER_SUCCESS = "Cập nhật nguời dùng thành công!";
        public static final String DELETE_USER_SUCCESS = "Xoá người dùng thành công!";
        public static final String PASSWORD_INCORRECT = "Mật khẩu không chính xác!";
        public static final String CHANGE_PASSWORD_SUCCESS = "Đổi mật khẩu thành công!";
        public static final String RESET_PASSWORD_SUCCESS = "Cập nhật mật khẩu về ban đầu!";
        public static final String ADD_ROLE_ADMIN_SUCCESS = "Cấp quyền admin thành công!";
        public static final String REMOVE_ROLE_ADMIN_SUCCESS ="Xoá quyền admin thành công!";
        public User() {
        }
    }
    public static class Product{
        public static final String PRODUCT_NOT_FOUND = "Không tìm thấy sách!";
        public static final String UPDATE_PRODUCT_SUCCESS = "Cập nhật sách thành công!";
        public static final String DELETE_PRODUCT_SUCCESS = "Xoá sách thành công!";
        public static final String CREATE_PRODUCT_SUCCESS = "Thêm sách thành công!";
        public static final String PRODUCT_OUT_OF_QUANTITY ="Sách không đủ số lượng để mua!";
    }
    public static class ShopCart{
        public static final String ADD_PRODUCT_TO_SHOPCART_WRONG = "Thêm sách không thành công!";
        public static final String PRODUCT_ON_SHOPCART_NOT_FOUND = "Không thêm thấy sách trong giỏ hàng!";
        public static final String REMOVE_PRODUCT_FROM_SHOPCART_SUCCESS = "Xoá sách trong giỏ hàng thành công!";
        public static final String ADD_PRODUCT_TO_SHOPCART_SUCCESS= "Thêm sách thành công vào giỏ hàng!";
        public static final String SHOPCART_IS_EMPTY = "Không có sách nào ở trong giỏ hàng!";
        public static final String SHOPCART_ALREADY_EXISTED = "Sách đã có trong giỏ hàng!";
        public static final String SHOPCART_NOT_FOUND = "Không tìm thấy sách trong giỏ hàng!";
    }

    public static class Bill{
        public static final String  ORDER_SUCCESS = "Đặt hàng thành công!";
        public static final String BILL_NOT_FOUND = "Đơn hàng không tồn tại!";
    }

    public static class UserRole{
        public static final String USER_ALREADY_ADMIN= "User đã là admin!";
        public static final String USER_NOT_ADMIN = "User không là admin!";
    }
    public static class Common{
        public static final String SUCCESS = "Thành công!";
        public static final String LOGIN_SUCCESS = "Đăng nhập thành công!";
        public static final String REGISTER_SUCCESS = "Đăng ký thành công!";
        public static final String WRONG = "Thất bại!";

    }

}
