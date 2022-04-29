package ipvc.estg.westseatraceability.clients;

import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "smartContractTraceabilityApi", url = "http://localhost:8801/invoke/my-channel1/trace-westsea")
//FIXME: url, channel and chaincode on properties
public interface SmartContractTraceabilityApiClient {

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    Map<String, String> createProductLot(@RequestHeader("Authorization") String bearerToken, String jsonRequest);

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    Map<String, String> createActivity(@RequestHeader("Authorization") String bearerToken, String jsonRequest);

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    Map<String, List<ProductLot>> getAllProductLots(@RequestHeader("Authorization") String bearerToken, String jsonRequest);

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    Map<String, List<Activity>> getAllActivities(@RequestHeader("Authorization") String bearerToken, String jsonRequest);

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    Map<String, List<Activity>> getTraceability(@RequestHeader("Authorization") String bearerToken, String jsonRequest);
}
