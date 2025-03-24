package bookstore.Exception;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.ThrowableProblem;

@Getter
@Setter
public class DataInvalidException extends ThrowableProblem {
    private final String errMessage;
    private final Long code;
    private final Object object;

    public DataInvalidException(String errMessage, Long code, Object object) {
        super();
        this.errMessage = errMessage;
        this.code = code;
        this.object = object;
    }
}
