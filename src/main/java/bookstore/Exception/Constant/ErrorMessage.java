package bookstore.Exception.Constant;

public class ErrorMessage {
    public ErrorMessage() {
    }

    public static class User{
        public static final String USER_NOT_FOUND = "Không tìm thấy người dùng";
        public static final String PERMISSION_DENIED = "Không có quyền truy cập!";
        public static final String EMAIL_OR_PASSWORD_INCORRECT = "Email hoặc mật khẩu không chính xác!";
        public static final String USER_ALREADY_EXISTS = "Email đã tồn tại";
        public static final String USER_NOT_EXISTS = "Email không tồn tại";
        public static final String RE_PASSWORD_NOT_MATCH = "Mật khẩu nhập lại không chính xác";
        public User() {
        }
    }
    public static class Product{
        public static final String PRODUCT_NOT_FOUND = "Không tìm thấy sách";
    }
    public static class ShopCart{
        public static final String ADD_PRODUCT_TO_SHOPCART_WRONG = "Thêm sách không thành công";
        public static final String PRODUCT_ON_SHOPCART_NOT_FOUND = "Không thêm thấy sách trong giỏ hàng";
        public static final String REMOVE_PRODUCT_FROM_SHOPCART_SUCCESS = "Xoá sách trong giỏ hàng thành công";
        public static final String ADD_PRODUCT_TO_SHOPCART_SUCCESS= "Thêm sách thành công vào giỏ hàng";
        public static final String SHOPCART_IS_EMPTY = "Không có sách nào ở trong giỏ hàng";
        public static final String SHOPCART_ALREADY_EXISTED = "Sách đã có trong giỏ hàng ";
        public static final String SHOPCART_NOT_FOUND = "Không tìm thấy sách trong giỏ hàng";
    }

    public static class Bill{
        public static final String  ORDER_SUCCESS = "Đặt hàng thành công";
    }
    public static class Common{
        public static final String SUCCESS = "Thành công";
        public static final String LOGIN_SUCCESS = "Đăng nhập thành công";

    }

}
