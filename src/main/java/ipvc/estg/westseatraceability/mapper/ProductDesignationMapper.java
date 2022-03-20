package ipvc.estg.westseatraceability.mapper;

import ipvc.estg.westseatraceability.dto.CreateProductDesignationDto;
import ipvc.estg.westseatraceability.dto.ProductDesignationDto;
import ipvc.estg.westseatraceability.model.ProductDesignation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDesignationMapper {

    public ProductDesignation createDtoToProductDesignation(CreateProductDesignationDto dto) {
        ProductDesignation productDesignation = new ProductDesignation();
        productDesignation.setDesignation(dto.getDesignation());

        return productDesignation;
    }

    public ProductDesignationDto productDesignationToDto(ProductDesignation productDesignation) {
        return ProductDesignationDto.builder()
                .id(productDesignation.getId())
                .designation(productDesignation.getDesignation())
                .build();
    }

    public List<ProductDesignationDto> productDesignationListToDto(List<ProductDesignation> productDesignations) {
        return productDesignations.stream().map(this::productDesignationToDto).collect(Collectors.toList());
    }
}
