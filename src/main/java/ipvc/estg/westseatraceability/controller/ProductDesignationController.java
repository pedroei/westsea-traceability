package ipvc.estg.westseatraceability.controller;

import ipvc.estg.westseatraceability.dto.CreateProductDesignationDto;
import ipvc.estg.westseatraceability.dto.ProductDesignationDto;
import ipvc.estg.westseatraceability.service.ProductDesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-designation")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ProductDesignationController implements ProductDesignationControllerContract {

    private final ProductDesignationService productDesignationService;

    @Override
    @PostMapping
    public ResponseEntity<ProductDesignationDto> create(@RequestBody CreateProductDesignationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productDesignationService.createProductDesignation(dto));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<ProductDesignationDto> get(@PathVariable String id) {
        return ResponseEntity.ok().body(productDesignationService.getProductDesignation(id));
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<List<ProductDesignationDto>> getAll() {
        return ResponseEntity.ok().body(productDesignationService.getAllProductDesignations());
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<ProductDesignationDto> update(@PathVariable String id, @RequestBody CreateProductDesignationDto dto) {
        return ResponseEntity.ok().body(productDesignationService.updateProductDesignation(id, dto));
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        productDesignationService.deleteProductDesignation(id);
    }
}
