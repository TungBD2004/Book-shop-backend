package bookstore.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Tên không được để trống")
    @Size(min =5 ,max = 50, message = "Tên không hợp lệ ")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Tên không được chứa ký tự đặc biệt")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5 ,max = 254, message = "Email không hợp lệ")
    private String email;


    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min =5 ,max = 254, message = "Địa chỉ không hợp lệ ")
    @Pattern(regexp = "^[\\p{L}0-9\\s]+$", message = "Địa chỉ không được chứa ký tự đặc biệt")
    private String address;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 5, max = 50, message = "Mật khẩu phải có từ 5 đến 50 ký tự")
    @Pattern(regexp = "^\\S+$", message = "Mật khẩu không được chứa khoảng trắng")
    private String password;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
    @Size(min = 5, max = 50, message = "Nhập lại mật khẩu phải có từ 5 đến 50 ký tự")
    @Pattern(regexp = "^\\S+$", message = "Mật khẩu không được chứa khoảng trắng")
    private String rePassword;
}
