package ipvc.estg.westseatraceability.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ipvc.estg.westseatraceability.dto.CreateProductDesignationDto;
import ipvc.estg.westseatraceability.dto.ProductDesignationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "westseatraceapi")
public interface ProductDesignationControllerContract {

    @Operation(summary = "Create a product designation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product designation created successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDesignationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<ProductDesignationDto> create(CreateProductDesignationDto dto);

    @Operation(summary = "Get an product designation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product designation retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDesignationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product designation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<ProductDesignationDto> get(String id);

    @Operation(summary = "Get all product designations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product designations retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDesignationDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<List<ProductDesignationDto>> getAll();

    @Operation(summary = "Update an product designation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product designation updated successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductDesignationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product designation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<ProductDesignationDto> update(String id, CreateProductDesignationDto dto);

    @Operation(summary = "Delete an product designation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product designation deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product designation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    void delete(String id);
}
