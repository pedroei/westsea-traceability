package ipvc.estg.westseatraceability.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipvc.estg.westseatraceability.clients.SmartContractEnrollApiClient;
import ipvc.estg.westseatraceability.clients.SmartContractTraceabilityApiClient;
import ipvc.estg.westseatraceability.clients.enums.ContractMethod;
import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.BlockchainUserProperties;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static ipvc.estg.westseatraceability.utils.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmartContractService {

    private final ObjectMapper objectMapper;
    private final SmartContractEnrollApiClient enrollApiClient;
    private final SmartContractTraceabilityApiClient traceabilityApiClient;
    private final BlockchainUserProperties blockchainUser;

    @SneakyThrows
    private String buildJsonRequest(Map<String, ?> jsonProperties) {
        return objectMapper.writeValueAsString(jsonProperties);
    }

    private String generateToken() {
        Map<String, String> jsonProperties = Map.of(
                ID, blockchainUser.getUser(),
                SECRET, blockchainUser.getPassword()
        );

        String jsonRequest = buildJsonRequest(jsonProperties);
        return enrollApiClient.enrollUser(jsonRequest).getToken();
    }

    private String getBearerToken() {
        return BEARER_PREFIX + generateToken();
    }

    public List<ProductLot> getAllProductLots() {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.READ_ALL_PRODUCT_LOTS.value,
                ARGS, List.of()
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, List<ProductLot>> response = traceabilityApiClient.getAllProductLots(bearerToken, jsonRequest);

        return response.get(RESPONSE);
    }

    public List<Activity> getAllActivities() {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.READ_ALL_ACTIVITIES.value,
                ARGS, List.of()
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, List<Activity>> response = traceabilityApiClient.getAllActivities(bearerToken, jsonRequest);

        return response.get(RESPONSE);
    }

    public List<Activity> getTraceability(String referenceNumber) {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.GET_TRACEABILITY.value,
                ARGS, List.of(referenceNumber)
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, List<Activity>> response = traceabilityApiClient.getTraceability(bearerToken, jsonRequest);

        return response.get(RESPONSE);
    }

    @SneakyThrows
    private String parseToString(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    public String createProductLot(CreateProductLotDto productLotDto) {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.CREATE_PRODUCT_LOT.value,
                ARGS, List.of(
                        productLotDto.getProductLotID(),
                        productLotDto.getReferenceNumber(),
                        productLotDto.getIsSerialNumber().toString(),
                        productLotDto.getDesignation(),
                        productLotDto.getProductType(),
                        productLotDto.getInitialAmount().toString(),
                        parseToString(productLotDto.getDocumentKeys())
                )
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, String> response = traceabilityApiClient.createProductLot(bearerToken, jsonRequest);

        return response.get(RESPONSE);
    }

    private String createParsedActivityOutput(CreateProductLotDto outputProductLot) {
        var newProductLot = ProductLot.builder()
                .docType("productLot") // does not matter
                .id(outputProductLot.getProductLotID())
                .referenceNumber(outputProductLot.getReferenceNumber())
                .isSerialNumber(outputProductLot.getIsSerialNumber())
                .designation(outputProductLot.getDesignation())
                .productType(outputProductLot.getProductType())
                .initialQuantity(outputProductLot.getInitialAmount())
                .availableQuantity(outputProductLot.getInitialAmount()) // does not matter
                .documentKeys(outputProductLot.getDocumentKeys())
                .build();

        return parseToString(newProductLot);
    }

    public String createActivity(CreateActivityDto activityDto) {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.CREATE_ACTIVITY.value,
                ARGS, List.of(
                        activityDto.getActivityID(),
                        activityDto.getDesignation(),
                        activityDto.getUserId(), //FIXME: can come from the token
                        parseToString(activityDto.getInputProductLots()),
                        createParsedActivityOutput(activityDto.getOutputProductLot())
                )
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, String> response = traceabilityApiClient.createActivity(bearerToken, jsonRequest);

        return response.get(RESPONSE);
    }
}
