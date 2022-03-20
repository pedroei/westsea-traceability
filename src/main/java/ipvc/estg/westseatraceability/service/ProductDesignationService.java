package ipvc.estg.westseatraceability.service;

import ipvc.estg.westseatraceability.dto.CreateProductDesignationDto;
import ipvc.estg.westseatraceability.dto.ProductDesignationDto;
import ipvc.estg.westseatraceability.exception.BadRequestException;
import ipvc.estg.westseatraceability.exception.NotFoundException;
import ipvc.estg.westseatraceability.mapper.ProductDesignationMapper;
import ipvc.estg.westseatraceability.model.ProductDesignation;
import ipvc.estg.westseatraceability.repository.ProductDesignationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductDesignationService {

    private final ProductDesignationRepository productDesignationRepository;
    private final ProductDesignationMapper productDesignationMapper;

    public List<ProductDesignationDto> getAllProductDesignations() {
        log.info("Fetching all product designations");
        List<ProductDesignation> productDesignations = productDesignationRepository.findAll();

        return productDesignationMapper.productDesignationListToDto(productDesignations);
    }

    public ProductDesignationDto getProductDesignation(String id) {
        log.info("Fetching product designation with id: " + id);

        return productDesignationRepository
                .findById(id)
                .map(productDesignationMapper::productDesignationToDto)
                .orElseThrow(() -> new NotFoundException("Product designation with id: " + id + " was not found"));
    }

    public ProductDesignationDto createProductDesignation(CreateProductDesignationDto dto) {
        log.info("Creating new product designation: {} ", dto.getDesignation());

        productDesignationRepository
                .findByDesignation(dto.getDesignation())
                .ifPresent(user -> {
                    throw new BadRequestException("Product designation with designation: " + user.getDesignation() + " already exists");
                });

        ProductDesignation productDesignation = productDesignationMapper.createDtoToProductDesignation(dto);
        ProductDesignation newProductDesignation = productDesignationRepository.save(productDesignation);

        return productDesignationMapper.productDesignationToDto(newProductDesignation);
    }

    public ProductDesignationDto updateProductDesignation(String id, CreateProductDesignationDto dto) {
        log.info("Updating product designation with id: {} ", id);

        productDesignationRepository
                .findByDesignation(dto.getDesignation())
                .ifPresent(user -> {
                    throw new BadRequestException("Product designation with designation: " + user.getDesignation() + " already exists");
                });

        ProductDesignation productDesignation = productDesignationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product designation with id: " + id + " was not found"));

        productDesignation.setDesignation(dto.getDesignation());
        ProductDesignation updatedProductDesignation = productDesignationRepository.save(productDesignation);

        return productDesignationMapper.productDesignationToDto(updatedProductDesignation);
    }

    public void deleteProductDesignation(String id) {
        log.info("Deleting product designation with id: {}", id);

        ProductDesignation productDesignation = productDesignationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Product designation with id: " + id + " was not found"));

        productDesignationRepository.delete(productDesignation);
    }
}
