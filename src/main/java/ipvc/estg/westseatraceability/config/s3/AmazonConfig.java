package ipvc.estg.westseatraceability.config.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {
        //FIXME: add to properties
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIARZIVDAGRWC7BOPML",
                "El442aRk5abzwMPP7bCsvYuWIYWBB5E2csYViXTy"
        );

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_3)
                .build();
    }
}
