package ipvc.estg.westseatraceability.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients({"ipvc.estg.westseatraceability.clients"})
public class FeignConfig {

    //TODO: decoder
}
