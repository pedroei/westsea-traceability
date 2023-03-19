package ipvc.estg.westseatraceability.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipvc.estg.westseatraceability.clients.SmartContractEnrollApiClient;
import ipvc.estg.westseatraceability.clients.SmartContractTraceabilityApiClient;
import ipvc.estg.westseatraceability.clients.model.*;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import ipvc.estg.westseatraceability.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static ipvc.estg.westseatraceability.utils.Constants.RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SmartContractServiceTest {

    @Mock
    private SmartContractEnrollApiClient enrollApiClient;

    @Mock
    private SmartContractTraceabilityApiClient traceabilityApiClient;

    @Mock
    private BlockchainUserProperties blockchainUser;

    @Mock
    private UserService userService;

    @Mock
    private FileService fileService;

    private SmartContractService smartContractService;

    @BeforeEach
    void setUp() {
        smartContractService = new SmartContractService(new ObjectMapper(), enrollApiClient, traceabilityApiClient, blockchainUser, userService, fileService);

        when(enrollApiClient.enrollUser(anyString())).thenReturn(new TokenResponse("token"));
        when(blockchainUser.getUser()).thenReturn("user");
        when(blockchainUser.getPassword()).thenReturn("password");
    }

    @Test
    void testGetAllProductLots() {
        var bearerToken = "Bearer token";
        var productLots = Arrays.asList(ProductLot.builder().id("1").build(), ProductLot.builder().id("2").build());

        when(traceabilityApiClient.getAllProductLots(eq(bearerToken), any(String.class))).thenReturn(Map.of("response", productLots));

        var result = smartContractService.getAllProductLots();

        assertEquals(productLots, result);
        verify(traceabilityApiClient).getAllProductLots(eq(bearerToken), any(String.class));
    }

    @Test
    void testFindProductLot() {
        var bearerToken = "Bearer token";
        var productLot = ProductLot.builder().id("1").build();
        var productLotId = UUID.randomUUID().toString();

        when(traceabilityApiClient.getProductLot(eq(bearerToken), any(String.class))).thenReturn(Map.of("response", productLot));

        var result = smartContractService.findProductLot(productLotId);

        assertEquals(productLot, result);
        verify(traceabilityApiClient).getProductLot(eq(bearerToken), any(String.class));
    }

    @Test
    void testGetAllActivities() {
        var bearerToken = "Bearer token";
        var activities = Arrays.asList(new Activity(), new Activity());

        when(traceabilityApiClient.getAllActivities(eq(bearerToken), any(String.class))).thenReturn(Map.of("response", activities));

        var result = smartContractService.getAllActivities();

        assertEquals(activities, result);
        verify(traceabilityApiClient).getAllActivities(eq(bearerToken), any(String.class));
    }

    @Test
    void testGetTraceability() {
        var referenceNumber = "12345";
        var traceability = ProductTraceability.builder()
                .id("1")
                .referenceNumber(referenceNumber)
                .isSerialNumber(false)
                .designation("Test product")
                .productType("Test product type")
                .initialQuantity(100f)
                .availableQuantity(90f)
                .usedQuantityAsInput(10f)
                .documentKeys(List.of(DocumentKey.builder().documentKey("key1").fileFingerPrint("fingerprint1").build()))
                .activity(ActivityTraceability.builder().id("1").build()).build();
        var responseMap = Map.of("response", traceability);

        when(traceabilityApiClient.getTraceability(anyString(), anyString())).thenReturn(responseMap);

        var actualTraceability = smartContractService.getTraceability(referenceNumber);

        assertEquals(traceability, actualTraceability);
    }

    @Test
    void testCreateProductLot() {
        var createProductLotRequest = CreateProductLotDto.builder()
                .referenceNumber("123abc")
                .isSerialNumber(true)
                .designation("anchor")
                .productType("part")
                .initialAmount(1f)
                .documents(List.of())
                .build();

        var responseMap = Map.of(
                "response",
                "{\"referenceNumber\":\"123abc\",\"isSerialNumber\":true,\"designation\":\"anchor\"," +
                        "\"productType\":\"part\",\"initialAmount\":1.0,\"documents\":[]}"
        );

        when(traceabilityApiClient.createProductLot(anyString(), any())).thenReturn(responseMap);

        var result = smartContractService.createProductLot(createProductLotRequest);

        verify(fileService, never()).uploadDocuments(anyList(), anyString());
        assertEquals(responseMap.get(RESPONSE), result);
    }

    @Test
    void testCreateActivity() {
        var createActivityRequest = CreateActivityDto.builder()
                .inputProductLots(Map.of("123abc", 4f))
                .designation("activity")
                .outputReferenceNumber("asd123")
                .outputIsSerialNumber(false)
                .outputDesignation("steel bar")
                .outputProductType("component")
                .outputInitialAmount(120f)
                .outputDocuments(List.of(mock(MultipartFile.class)))
                .build();

        var responseMap = Map.of("response", "j");

        when(userService.getUserByUsername(anyString())).thenReturn(new User("1", "user", "user1", "", Set.of()));
        when(traceabilityApiClient.createActivity(anyString(), any())).thenReturn(responseMap);
        when(fileService.uploadDocuments(anyList(), anyString())).thenReturn(List.of(DocumentKey.builder().documentKey("key1").fileFingerPrint("kdajgwbkyauw").build()));
        when(traceabilityApiClient.updateProductLotDocumentKeys(anyString(), anyString())).thenReturn(Map.of(RESPONSE, ""));

        var result = smartContractService.createActivity("user1", createActivityRequest);

        verify(fileService).uploadDocuments(anyList(), anyString());
        verify(traceabilityApiClient).updateProductLotDocumentKeys(anyString(), anyString());
        assertEquals(responseMap.get(RESPONSE), result);
    }

    @Test
    void testUpdateProductLotDocumentKeys() {
        var req = List.of(
                DocumentKey.builder()
                        .documentKey("key1")
                        .fileFingerPrint("akwdh")
                        .build()
        );

        when(traceabilityApiClient.updateProductLotDocumentKeys(anyString(), anyString()))
                .thenReturn(Map.of(RESPONSE, ""));

        smartContractService.updateProductLotDocumentKeys("1", req);

        verify(traceabilityApiClient, times(1)).updateProductLotDocumentKeys(anyString(), anyString());
    }
}
