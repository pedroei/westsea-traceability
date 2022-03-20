package ipvc.estg.westseatraceability.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipvc.estg.westseatraceability.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class JwtTokenHelper {

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm createAlgorithm() {
        return Algorithm.HMAC256(secret.getBytes());
    }

    public String createAccessToken(org.springframework.security.core.userdetails.User user, HttpServletRequest request, List<String> claim) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) //10 minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", claim)
                .sign(createAlgorithm());
    }

    public String createAccessToken(User user, HttpServletRequest request, List<String> claim) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) //10 minutes
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", claim)
                .sign(createAlgorithm());
    }

    public String createRefreshToken(org.springframework.security.core.userdetails.User user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) //30 minutes
                .withIssuer(request.getRequestURL().toString())
                .sign(createAlgorithm());
    }

    @SneakyThrows
    public void addTokensToResponse(HttpServletResponse response, String accessToken, String refreshToken) {
        //        response.setHeader("access_token", access_token);
        //        response.setHeader("refresh_token", refresh_token);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    public String decodeRefreshTokenJwt(String refreshToken) {
        Algorithm algorithm = createAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        return decodedJWT.getSubject();
    }

    public UsernamePasswordAuthenticationToken decodeAccessTokenJwt(String accessToken) {
        Algorithm algorithm = createAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(accessToken);
        String username = decodedJWT.getSubject();

        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    @SneakyThrows
    public void sendError(HttpServletResponse response, Exception e) {
        response.setHeader("error", e.getMessage());
        response.setStatus(FORBIDDEN.value());
        // response.sendError(FORBIDDEN.value());

        Map<String, String> error = new HashMap<>();
        error.put("error_message", e.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
