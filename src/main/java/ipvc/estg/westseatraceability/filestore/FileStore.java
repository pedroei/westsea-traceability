package ipvc.estg.westseatraceability.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
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

    public byte[] download(String path, String documentKey, String documentFingerPrint) {
        try {
            var object = s3.getObject(path, documentKey);
            var inputStream = object.getObjectContent();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            checkFileReliability(documentFingerPrint, bytes);

            return bytes;
        } catch (IOException e) {
            log.error("Failed to store file in S3, e: {}", e.getMessage());
            throw new IllegalStateException("Failed to download file in S3", e);
        }
    }

    private void checkFileReliability(String documentFingerPrint, byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        String checksumSHA256 = DigestUtils.sha256Hex(inputStream);

        if (!checksumSHA256.equals(documentFingerPrint)) {
            throw new IllegalStateException("The finger prints don't match, so the file was modified");
        }

        inputStream.close();
    }
}
