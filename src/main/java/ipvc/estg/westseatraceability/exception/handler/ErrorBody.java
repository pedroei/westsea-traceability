package ipvc.estg.westseatraceability.exception.handler;

import ipvc.estg.westseatraceability.exception.ExceptionDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
class ErrorBody {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<ExceptionDetail> details;
}
