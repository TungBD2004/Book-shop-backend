package bookstore.Exception.Constant;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BSResponseEntity {
    private String message;
    private Long code;
    private Object object;
}