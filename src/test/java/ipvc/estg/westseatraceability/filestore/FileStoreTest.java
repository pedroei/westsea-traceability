package ipvc.estg.westseatraceability.filestore;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileStoreTest {

    @Mock
    private AmazonS3 s3;

    @InjectMocks
    private FileStore fileStore;

    @Test
    void testDownload() {
        var s3Object = mock(S3Object.class);
        var testBytes = "test".getBytes();
        var testDocumentFingerPrint = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

        when(s3.getObject(anyString(), anyString())).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(new S3ObjectInputStream(new ByteArrayInputStream(testBytes), mock(HttpRequestBase.class)));
        assertDoesNotThrow(() -> fileStore.download("/path", "test", testDocumentFingerPrint));
    }

    @Test
    void testDownloadWithDifferentFingerprints() {
        var s3Object = mock(S3Object.class);
        var testBytes = "test".getBytes();

        when(s3.getObject(anyString(), anyString())).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(new S3ObjectInputStream(new ByteArrayInputStream(testBytes), mock(HttpRequestBase.class)));
        assertThrows(IllegalStateException.class, () -> fileStore.download("/path", "test", "abc"));
    }
}
