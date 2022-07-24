package ipvc.estg.westseatraceability.controller;

import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import ipvc.estg.westseatraceability.clients.model.ProductTraceability;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import ipvc.estg.westseatraceability.service.SmartContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    @PostMapping(value = "/product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createProductLot(@ModelAttribute CreateProductLotDto productLotDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(smartContractService.createProductLot(productLotDto));
    }

    @Override
    @PostMapping(value = "/activity", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createActivity(Principal principal, @ModelAttribute CreateActivityDto activityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(smartContractService.createActivity(principal.getName(), activityDto));
    }

    @Override
    @GetMapping("/product/{productLotUuid}/document/{documentKey}/download")
    public ResponseEntity<byte[]> getDocument(@PathVariable String productLotUuid, @PathVariable String documentKey, HttpServletResponse response) {
        var inputStream = smartContractService.getDocument(productLotUuid, documentKey, response);

        return ResponseEntity.ok()
                .body(inputStream.toByteArray());
    }
}
