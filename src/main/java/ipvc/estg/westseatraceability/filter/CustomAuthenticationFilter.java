package ipvc.estg.westseatraceability.filter;

import ipvc.estg.westseatraceability.helper.JwtTokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenHelper jwtTokenHelper;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenHelper jwtTokenHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Login attempt with Username: {}, and Password: {}", username, password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String accessToken = jwtTokenHelper.createAccessToken(user, request, roles);
        String refreshToken = jwtTokenHelper.createRefreshToken(user, request);

        jwtTokenHelper.addTokensToResponse(response, accessToken, refreshToken);
    }
}
