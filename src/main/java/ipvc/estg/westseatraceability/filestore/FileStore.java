package ipvc.estg.westseatraceability.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStore {

    private final AmazonS3 s3;

    public void save(String path,
                     String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {
        var metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try {
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            log.error("Failed to store file in S3, e: {}", e.getMessage());
            throw new IllegalStateException("Failed to store file in S3", e);
        }
    }

    public void download(String path, String documentKey, HttpServletResponse response) {
        try {
            var object = s3.getObject(path, documentKey);
            //FIXME: find better way to download file
            IOUtils.copy(object.getObjectContent().getDelegateStream(), response.getOutputStream());
            response.flushBuffer();
        } catch (AmazonServiceException | IOException e) {
            log.error("Failed to store file in S3, e: {}", e.getMessage());
            throw new IllegalStateException("Failed to download file in S3", e);
        }
    }
}
