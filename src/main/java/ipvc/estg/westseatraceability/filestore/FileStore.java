package ipvc.estg.westseatraceability.filestore;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
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

    public ByteArrayOutputStream download(String path, String documentKey, HttpServletResponse response, String documentFingerPrint) {
        try {
            var object = s3.getObject(path, documentKey);
            checkFileReliability(documentFingerPrint, object.getObjectContent().getDelegateStream());

            InputStream is = object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            response.setContentLength((int) object.getObjectMetadata().getContentLength());
            response.setContentType(object.getObjectMetadata().getUserMetaDataOf("content-type"));
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"response\"");

            return outputStream;
        } catch (IOException | AmazonClientException e) {
            log.error("Failed to store file in S3, e: {}", e.getMessage());
            throw new IllegalStateException("Failed to download file in S3", e);
        }
    }

    private void checkFileReliability(String documentFingerPrint, InputStream fileInputStream) throws IOException {
        String checksumSHA256 = DigestUtils.sha256Hex(fileInputStream);
        if (!checksumSHA256.equals(documentFingerPrint)) {
            throw new IllegalStateException("The finger prints don't match, so the file was modified");
        }
    }
}
