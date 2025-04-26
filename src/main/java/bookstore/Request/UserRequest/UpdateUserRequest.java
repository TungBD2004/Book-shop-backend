package bookstore.Request.UserRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    @NotBlank(message = "Tên không dược trống")
    @Size(min = 1, max = 55, message = "Tên không hợp lệ")
    private String name;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min= 1 , max = 255, message = "Địa chỉ không hợp lệ")
    private String address;
}
