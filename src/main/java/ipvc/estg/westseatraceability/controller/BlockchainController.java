package ipvc.estg.westseatraceability.controller;

import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import ipvc.estg.westseatraceability.clients.model.ProductTraceability;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import ipvc.estg.westseatraceability.service.SmartContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_CLIENT', 'ROLE_ADMIN')")
    public ResponseEntity<List<ProductLot>> getProductLots() {
        return ResponseEntity.ok().body(smartContractService.getAllProductLots());
    }

    @Override
    @GetMapping("/activity")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_CLIENT', 'ROLE_ADMIN')")
    public ResponseEntity<List<Activity>> getActivities() {
        return ResponseEntity.ok().body(smartContractService.getAllActivities());
    }

    @Override
    @GetMapping("{referenceNumber}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_CLIENT', 'ROLE_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_CLIENT', 'ROLE_ADMIN')")
    public ResponseEntity<ByteArrayResource> getDocument(@PathVariable String productLotUuid, @PathVariable String documentKey) {
        var data = smartContractService.getDocument(productLotUuid, documentKey);
        var filename = "file.pdf"; //only pdf for now

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + filename + "\"")
                .body(new ByteArrayResource(data));
    }
}
