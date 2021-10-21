package curso.springboot.domain.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -284190489465721142L;

    private Map<String, Object> error;

    public ValidationException(Map<String, Object> error) {
        super("Validation Exception");
        this.error = error;
    }

    public ValidationException(String msg) {
        super(msg);
    }


    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }
}
