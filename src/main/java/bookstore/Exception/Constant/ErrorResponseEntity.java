package bookstore.Exception.Constant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorResponseEntity {
    private String message;
    private String code;
    private String object;
}