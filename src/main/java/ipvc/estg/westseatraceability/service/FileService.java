package ipvc.estg.westseatraceability.service;

import ipvc.estg.westseatraceability.clients.model.DocumentKey;
import ipvc.estg.westseatraceability.config.s3.AwsS3Properties;
import ipvc.estg.westseatraceability.filestore.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileStore fileStore;
    private final AwsS3Properties awsS3Properties;

    public List<DocumentKey> uploadDocuments(List<MultipartFile> files, String productLotUuid) {
        if (files.isEmpty()) return Collections.emptyList();

        return files.stream()
                .map(file -> uploadDocument(file, productLotUuid))
                .filter(Objects::nonNull)
                .toList();
    }

    public DocumentKey uploadDocument(MultipartFile file, String productLotUuid) {
        if (file.isEmpty()) return null;

        String documentKey;
        String documentFingerprint;

        var metadata = new HashMap<String, String>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        var path = String.format("%s/%s", awsS3Properties.getBucketName(), productLotUuid);
        var filename = String.format("%s-%s", file.getName(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            documentKey = filename;
            documentFingerprint = DigestUtils.sha256Hex(file.getInputStream());
        } catch (IOException e) {
            log.error("Failed uploading file, e: {}", e.getMessage());
            throw new IllegalStateException(e);
        }

        return DocumentKey
                .builder()
                .documentKey(documentKey)
                .fileFingerPrint(documentFingerprint)
                .build();
    }

    public ByteArrayOutputStream getDocument(String productLotUuid, String documentKey,
                                             HttpServletResponse response, String documentFingerPrint) {
        var path = String.format("%s/%s", awsS3Properties.getBucketName(), productLotUuid);
        return fileStore.download(path, documentKey, response, documentFingerPrint);
    }
}
