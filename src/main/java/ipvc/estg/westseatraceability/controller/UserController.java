package ipvc.estg.westseatraceability.controller;

import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.dto.RoleToUserDto;
import ipvc.estg.westseatraceability.dto.UserDto;
import ipvc.estg.westseatraceability.service.TokenService;
import ipvc.estg.westseatraceability.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
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
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenService.refreshToken(request, response);
    }

    @GetMapping("/client")
    public String testClient() {
        return "endpoint works fine for: client";
    }

    @GetMapping("/employee")
    public String testEmployee() {
        return "endpoint works fine for: employee";
    }
}
