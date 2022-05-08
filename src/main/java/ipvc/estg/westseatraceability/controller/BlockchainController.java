package ipvc.estg.westseatraceability.controller;

import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import ipvc.estg.westseatraceability.clients.model.ProductTraceability;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import ipvc.estg.westseatraceability.service.SmartContractService;
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

    @Override
    @GetMapping("/product")
    public ResponseEntity<List<ProductLot>> getProductLots() {
        return ResponseEntity.ok().body(smartContractService.getAllProductLots());
    }

    @Override
    @GetMapping("/activity")
    public ResponseEntity<List<Activity>> getActivities() {
        return ResponseEntity.ok().body(smartContractService.getAllActivities());
    }

    @Override
    @GetMapping("{referenceNumber}")
    public ResponseEntity<ProductTraceability> getTraceability(@PathVariable String referenceNumber) {
        return ResponseEntity.ok().body(smartContractService.getTraceability(referenceNumber));
    }

    @Override
    @PostMapping("/product")
    public ResponseEntity<String> createProductLot(@RequestBody CreateProductLotDto productLotDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(smartContractService.createProductLot(productLotDto));
    }

    @Override
    @PostMapping("/activity")
    public ResponseEntity<String> createActivity(Principal principal, @RequestBody CreateActivityDto activityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(smartContractService.createActivity(principal.getName(), activityDto));
    }
}
