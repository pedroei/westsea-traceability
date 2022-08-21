package ipvc.estg.westseatraceability.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipvc.estg.westseatraceability.clients.SmartContractEnrollApiClient;
import ipvc.estg.westseatraceability.clients.SmartContractTraceabilityApiClient;
import ipvc.estg.westseatraceability.clients.enums.ContractMethod;
import ipvc.estg.westseatraceability.clients.model.*;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ipvc.estg.westseatraceability.utils.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmartContractService {

    private final ObjectMapper objectMapper;
    private final SmartContractEnrollApiClient enrollApiClient;
    private final SmartContractTraceabilityApiClient traceabilityApiClient;
    private final BlockchainUserProperties blockchainUser;
    private final UserService userService;
    private final FileService fileService;

    //FIXME: when there is no activities/productLots the result of the fablo api IS NOT an empty array, it is a string. This causes a conversion error

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

    public ProductLot findProductLot(String productLotId) {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.READ_PRODUCT_LOT.value,
                ARGS, List.of(productLotId)
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, ProductLot> response = traceabilityApiClient.getProductLot(bearerToken, jsonRequest);

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

    public ProductTraceability getTraceability(String referenceNumber) {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.GET_TRACEABILITY.value,
                ARGS, List.of(referenceNumber)
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, ProductTraceability> response = traceabilityApiClient.getTraceability(bearerToken, jsonRequest);

        return response.get(RESPONSE);
    }

    @SneakyThrows
    private String parseToString(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    public String createProductLot(CreateProductLotDto productLotDto) {
        var bearerToken = getBearerToken();

        var productLotUuid = UUID.randomUUID().toString();
        List<DocumentKey> documentKeys = Collections.emptyList();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.CREATE_PRODUCT_LOT.value,
                ARGS, List.of(
                        productLotUuid,
                        productLotDto.getReferenceNumber(),
                        productLotDto.getIsSerialNumber().toString(),
                        productLotDto.getDesignation(),
                        productLotDto.getProductType(),
                        productLotDto.getInitialAmount().toString(),
                        parseToString(documentKeys)
                )
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, String> response = traceabilityApiClient.createProductLot(bearerToken, jsonRequest);

        if (productLotDto.getDocuments() != null && !productLotDto.getDocuments().isEmpty()) {
            documentKeys = fileService.uploadDocuments(productLotDto.getDocuments(), productLotUuid);
            updateProductLotDocumentKeys(productLotUuid, documentKeys);
        }

        return response.get(RESPONSE);
    }

    private String createParsedActivityOutput(CreateActivityDto activityDto, String productLotUuid) {
        List<DocumentKey> documentKeys = Collections.emptyList();

        var newProductLot = ProductLot.builder()
                .docType("productLot") // does not matter
                .id(productLotUuid)
                .referenceNumber(activityDto.getOutputReferenceNumber())
                .isSerialNumber(activityDto.getOutputIsSerialNumber())
                .designation(activityDto.getOutputDesignation())
                .productType(activityDto.getOutputProductType())
                .initialQuantity(activityDto.getOutputInitialAmount())
                .availableQuantity(activityDto.getOutputInitialAmount()) // does not matter
                .documentKeys(documentKeys)
                .build();

        return parseToString(newProductLot);
    }

    public String createActivity(String username, CreateActivityDto activityDto) {
        var loggedInUser = userService.getUserByUsername(username);
        var bearerToken = getBearerToken();

        var outputProductUuid = UUID.randomUUID().toString();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.CREATE_ACTIVITY.value,
                ARGS, List.of(
                        UUID.randomUUID().toString(),
                        activityDto.getDesignation(),
                        loggedInUser.getId(),
                        parseToString(activityDto.getInputProductLots()),
                        createParsedActivityOutput(activityDto, outputProductUuid)
                )
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, String> response = traceabilityApiClient.createActivity(bearerToken, jsonRequest);

        if (activityDto.getOutputDocuments() != null && !activityDto.getOutputDocuments().isEmpty()) {
            var documentKeys = fileService.uploadDocuments(activityDto.getOutputDocuments(), outputProductUuid);
            updateProductLotDocumentKeys(outputProductUuid, documentKeys);
        }

        return response.get(RESPONSE);
    }

    public void updateProductLotDocumentKeys(String productLotId, List<DocumentKey> newDocumentKeys) {
        var bearerToken = getBearerToken();

        Map<String, ?> jsonProperties = Map.of(
                METHOD, ContractMethod.UPDATE_PRODUCT_LOT_DOCUMENT_KEYS.value,
                ARGS, List.of(
                        productLotId,
                        parseToString(newDocumentKeys)
                )
        );

        String jsonRequest = buildJsonRequest(jsonProperties);

        Map<String, String> response = traceabilityApiClient.updateProductLotDocumentKeys(bearerToken, jsonRequest);

        log.info(response.get(RESPONSE));
    }

    public byte[] getDocument(String productLotUuid, String documentKey) {
        return findProductLot(productLotUuid)
                .getDocumentKeys()
                .stream()
                .filter(docKey -> docKey.getDocumentKey().equals(documentKey))
                .findFirst()
                .map(value -> fileService.getDocument(productLotUuid, documentKey, value.getFileFingerPrint())).orElse(null);
    }
}
