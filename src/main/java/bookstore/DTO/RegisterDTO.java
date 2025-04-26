package bookstore.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDTO {

    @NotBlank(message = "Tên không được để trống")
    @Size(min =5 ,max = 50, message = "Tên không hợp lệ ")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(min = 5 ,max = 254, message = "Email không hợp lệ")
    private String email;

    @Size(min =5 ,max = 254, message = "Địa chỉ không hợp lệ ")
    private String address;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 5, max = 50, message = "Mật khẩu phải có từ 5 đến 50 ký tự")
    private String password;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
    @Size(min = 5, max = 50, message = "Nhập lại mật khẩu phải có từ 5 đến 50 ký tự")
    private String rePassword;
}
