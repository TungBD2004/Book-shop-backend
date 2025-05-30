package bookstore.Util.Enum;

public enum BillStatusEnum {
    SHIPPED("Đang giao"),
    COMPLETED("Đã giao"),
    CANCELED("Đã hủy"),
    PENDING("Chờ xử lý");
    BillStatusEnum(String status) {
    }
}
