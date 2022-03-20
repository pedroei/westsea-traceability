package ipvc.estg.westseatraceability.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SecurityRequirement(name = "westseatraceapi")
public interface TokenControllerContract {

    @Operation(summary = "Refresh the current token that is expired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access is forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server error, based upon the context", content = @Content)})
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
