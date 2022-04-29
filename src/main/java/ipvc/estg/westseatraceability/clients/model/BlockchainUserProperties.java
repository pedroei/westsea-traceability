package ipvc.estg.westseatraceability.clients.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties("app.blockchain.network-admin")
@Setter
@Getter
public class BlockchainUserProperties {
    private String user;
    private String password;
}
