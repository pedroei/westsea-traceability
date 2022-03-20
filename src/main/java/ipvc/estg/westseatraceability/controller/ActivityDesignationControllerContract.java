package ipvc.estg.westseatraceability.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ipvc.estg.westseatraceability.dto.ActivityDesignationDto;
import ipvc.estg.westseatraceability.dto.CreateActivityDesignationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "westseatraceapi")
public interface ActivityDesignationControllerContract {

    @Operation(summary = "Create a activity designation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Activity designation created successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ActivityDesignationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<ActivityDesignationDto> create(CreateActivityDesignationDto dto);

    @Operation(summary = "Get an activity designation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity designation retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ActivityDesignationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Activity designation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<ActivityDesignationDto> get(String id);

    @Operation(summary = "Get all activity designations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity designations retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ActivityDesignationDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<List<ActivityDesignationDto>> getAll();

    @Operation(summary = "Update an activity designation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity designation updated successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ActivityDesignationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Activity designation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<ActivityDesignationDto> update(String id, CreateActivityDesignationDto dto);

    @Operation(summary = "Delete an activity designation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity designation deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Activity designation not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    void delete(String id);
}
