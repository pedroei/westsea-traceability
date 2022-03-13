package ipvc.estg.westseatraceability.controller;

import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.dto.RoleToUserDto;
import ipvc.estg.westseatraceability.dto.UserDto;
import ipvc.estg.westseatraceability.service.TokenService;
import ipvc.estg.westseatraceability.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping()
    public ResponseEntity<UserDto> saveUser(@RequestBody CreateUserDto dto) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(dto));
    }

    @PostMapping("/role")
    public ResponseEntity<Void> addRoleToUser(@RequestBody RoleToUserDto dto) {
        userService.addRoleToUser(dto.getUserId(), dto.getRole());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        tokenService.refreshToken(request, response);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping("/client")
    public String testClient() {
        return "endpoint works fine for: client";
    }

    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
    @GetMapping("/employee")
    public String testEmployee() {
        return "endpoint works fine for: employee";
    }
}
