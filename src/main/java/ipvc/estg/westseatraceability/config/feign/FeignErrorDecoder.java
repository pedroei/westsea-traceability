package ipvc.estg.westseatraceability.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import ipvc.estg.westseatraceability.exception.BadRequestException;
import ipvc.estg.westseatraceability.exception.NotFoundException;

import java.io.IOException;
import java.io.InputStream;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignErrorBody message;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, FeignErrorBody.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        return switch (response.status()) {
            case 400 -> new BadRequestException(message.getMessage() != null ? message.getMessage() : "Bad Request");
            case 404 -> new NotFoundException(message.getMessage() != null ? message.getMessage() : "Not found");
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
