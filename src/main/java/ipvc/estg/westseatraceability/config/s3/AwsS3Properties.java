package ipvc.estg.westseatraceability.config.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties("app.aws.s3")
@Setter
@Getter
public class AwsS3Properties {
    private String bucketName;
    private String accessKey;
    private String secretKey;
}
