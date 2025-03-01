package bookstore.Exception;

import lombok.Getter;
import lombok.Setter;
import org.zalando.problem.ThrowableProblem;

@Getter
@Setter
public class DataInvalidException extends ThrowableProblem {
    private final String errMessage;
    private final String code;
    private final String object;

    public DataInvalidException(String errMessage, String code, String object) {
        super();
        this.errMessage = errMessage;
        this.code = code;
        this.object = object;
    }
}
