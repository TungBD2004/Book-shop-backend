package bookstore.Request.UserRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    @NotBlank(message = "Tên không dược trống")
    @Size(min = 5, max = 55, message = "Tên không hợp lệ")
    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹ\\s]+$", message = "Tên không được chứa ký tự đặc biệt hoặc số")
    private String name;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min= 5 , max = 255, message = "Địa chỉ không hợp lệ")
    @Pattern(regexp = "^[\\p{L}0-9\\s]+$", message = "Địa chỉ không được chứa ký tự đặc biệt")
    private String address;
}
