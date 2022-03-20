package ipvc.estg.westseatraceability.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class DetailedException extends RuntimeException {

    private final List<ExceptionDetail> exceptionDetails;

    public DetailedException(List<ExceptionDetail> exceptionDetails) {
        super();
        this.exceptionDetails = exceptionDetails;
    }

    public DetailedException(List<ExceptionDetail> exceptionDetails, String message) {
        super(message);
        this.exceptionDetails = exceptionDetails;
    }

    public DetailedException(List<ExceptionDetail> exceptionDetails, String message, Throwable cause) {
        super(message, cause);
        this.exceptionDetails = exceptionDetails;
    }

}
