package ipvc.estg.westseatraceability.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import ipvc.estg.westseatraceability.clients.model.ProductTraceability;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import ipvc.estg.westseatraceability.service.SmartContractService;
import ipvc.estg.westseatraceability.utils.MockConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/traceability")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
@RequiredArgsConstructor
public class BlockchainController implements BlockchainControllerContract {

    private final SmartContractService smartContractService;

    private final ObjectMapper objectMapper;

    @Override
    @GetMapping("/product")
    public ResponseEntity<List<ProductLot>> getProductLots() throws JsonProcessingException {
        //return ResponseEntity.ok().body(smartContractService.getAllProductLots());
        var mockProductLots = objectMapper.readValue(
                MockConstants.MOCK_LIST_PRODUCT_LOT,
                new TypeReference<List<ProductLot>>() {
                });
        return ResponseEntity.ok().body(mockProductLots);
    }

    @Override
    @GetMapping("/activity")
    public ResponseEntity<List<Activity>> getActivities() throws JsonProcessingException {
        //return ResponseEntity.ok().body(smartContractService.getAllActivities());
        var mockActivities = objectMapper.readValue(
                MockConstants.MOCK_LIST_ACTIVITY,
                new TypeReference<List<Activity>>() {
                });
        return ResponseEntity.ok().body(mockActivities);
    }

    @Override
    @GetMapping("{referenceNumber}")
    public ResponseEntity<ProductTraceability> getTraceability(@PathVariable String referenceNumber) throws JsonProcessingException {
        //return ResponseEntity.ok().body(smartContractService.getTraceability(referenceNumber));
        var mockTraceability = objectMapper.readValue(
                MockConstants.MOCK_PRODUCT_LOT_TRACEABILITY,
                ProductTraceability.class);
        return ResponseEntity.ok().body(mockTraceability);
    }

    @Override
    @PostMapping("/product")
    public ResponseEntity<String> createProductLot(@RequestBody CreateProductLotDto productLotDto) {
        //return ResponseEntity.status(HttpStatus.CREATED).body(smartContractService.createProductLot(productLotDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Lot created successfully");
    }

    @Override
    @PostMapping("/activity")
    public ResponseEntity<String> createActivity(Principal principal, @RequestBody CreateActivityDto activityDto) {
        //return ResponseEntity.status(HttpStatus.CREATED).body(smartContractService.createActivity(principal.getName(), activityDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("Activity created successfully");
    }
}
