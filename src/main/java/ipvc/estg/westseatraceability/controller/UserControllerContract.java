package ipvc.estg.westseatraceability.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.dto.UpdateUserDto;
import ipvc.estg.westseatraceability.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "westseatraceapi")
public interface UserControllerContract {

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<List<UserDto>> getAllUsers();

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<UserDto> createUser(CreateUserDto dto);

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    ResponseEntity<UserDto> updateUser(String id, UpdateUserDto dto);
}
