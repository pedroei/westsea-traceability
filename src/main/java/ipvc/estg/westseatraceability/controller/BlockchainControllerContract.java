package ipvc.estg.westseatraceability.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ipvc.estg.westseatraceability.clients.model.Activity;
import ipvc.estg.westseatraceability.clients.model.ProductLot;
import ipvc.estg.westseatraceability.dto.CreateActivityDto;
import ipvc.estg.westseatraceability.dto.CreateProductLotDto;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

@SecurityRequirement(name = "westseatraceapi")
public interface BlockchainControllerContract {

    @Operation(summary = "Get all product lots")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Lots retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductLot.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<List<ProductLot>> getProductLots();

    @Operation(summary = "Get all activities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Activity.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<List<Activity>> getActivities();

    @Operation(summary = "Get all activities related to a certain ProductLot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Activity.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product lot with that referenceNumber not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<List<Activity>> getTraceability(String referenceNumber);

    @Operation(summary = "Create a product lot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product Lot created successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<String> createProductLot(CreateProductLotDto productLotDto);

    @Operation(summary = "Create an activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity created successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Input product lot not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<String> createActivity(Principal principal, CreateActivityDto activityDto);
}
