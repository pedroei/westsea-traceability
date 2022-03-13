package ipvc.estg.westseatraceability.service;

import ipvc.estg.westseatraceability.helper.JwtTokenHelper;
import ipvc.estg.westseatraceability.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Refresh token is missing");
        }

        try {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            String username = jwtTokenHelper.decodeRefreshTokenJwt(refreshToken);

            User user = userService.getUserByUsername(username);
            List<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toList());
            String accessToken = jwtTokenHelper.createAccessToken(user, request, roles);


            jwtTokenHelper.addTokensToResponse(response, accessToken, refreshToken);

        } catch (Exception e) {
            jwtTokenHelper.sendError(response, e);
        }

    }
}
