package bookstore.Exception;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.ThrowableProblem;

@Getter
@Setter
public class BookShopAuthenticationException extends ThrowableProblem {

    private final String errMessage;
    private final Long code;
    private final Object object;

    public BookShopAuthenticationException(String errMessage, Long code, Object object) {
        super();
        this.errMessage = errMessage;
        this.code = code;
        this.object = object;
    }
}
