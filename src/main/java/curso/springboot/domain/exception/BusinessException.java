package curso.springboot.domain.exception;

import java.util.Map;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4047125885325267412L;

    private Map<String, Object> error;

    public BusinessException(Map<String, Object> error) {
        super("Not Found Exception");
        this.error = error;
    }

    public BusinessException(String msg) {
        super(msg);
    }


    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }
}


