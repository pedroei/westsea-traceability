package ipvc.estg.westseatraceability.exception.handler;

import ipvc.estg.westseatraceability.exception.BadRequestException;
import ipvc.estg.westseatraceability.exception.DetailedException;
import ipvc.estg.westseatraceability.exception.ExceptionDetail;
import ipvc.estg.westseatraceability.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    Map<Class, HttpStatus> dict = new HashMap<>();

    public HttpExceptionHandler() {
        dict.put(NotFoundException.class, HttpStatus.NOT_FOUND);
        dict.put(BadRequestException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DetailedException.class})
    protected ResponseEntity<Object> handleDetailedException(DetailedException ex, WebRequest request) {

        List<ExceptionDetail> details = ex.getExceptionDetails();

        var status = dict.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        var errorBody = ErrorBody.builder()
                .timestamp(Instant.now())
                .message(ex.toString())
                .status(status.value())
                .error(ex.getMessage())
                .details(details)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {

        var status = dict.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        var errorBody = ErrorBody.builder()
                .timestamp(Instant.now())
                .message(ex.toString())
                .status(status.value())
                .error(ex.getMessage())
                .details(null)
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), status, request);
    }
}
