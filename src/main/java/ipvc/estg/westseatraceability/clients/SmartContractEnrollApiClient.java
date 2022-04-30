package ipvc.estg.westseatraceability.clients;

import ipvc.estg.westseatraceability.clients.model.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "smartContractEnrollApi", url = "${app.blockchain.api.base-url}")
public interface SmartContractEnrollApiClient {

    @PostMapping(value = "/user/enroll", consumes = APPLICATION_JSON_VALUE)
    TokenResponse enrollUser(String jsonRequest);
}
