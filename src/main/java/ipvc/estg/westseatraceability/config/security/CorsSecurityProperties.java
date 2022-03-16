package ipvc.estg.westseatraceability.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

import static ipvc.estg.westseatraceability.utils.Constants.*;

@Component
@Configuration
@ConfigurationProperties("app.http.security")
@Setter
@Getter
public class CorsSecurityProperties {
    private boolean enabled;
    private List<String> cors;
    private List<String> headers;
    private List<String> methods = List.of(GET, POST, PUT, DELETE, OPTIONS, HEAD);
    private List<String> exposedHeaders;
}
