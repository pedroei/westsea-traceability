package ipvc.estg.westseatraceability.config.security;

import ipvc.estg.westseatraceability.filter.CustomAuthenticationFilter;
import ipvc.estg.westseatraceability.filter.CustomAuthorizationFilter;
import ipvc.estg.westseatraceability.helper.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenHelper jwtTokenHelper;
    private final CorsSecurityProperties corsProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), jwtTokenHelper);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.cors(cors -> cors.configurationSource(request -> corsConfiguration()));
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/v1/token/refresh/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtTokenHelper), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private CorsConfiguration corsConfiguration() {
        var cors = new CorsConfiguration();
        cors.setAllowedOrigins(corsProperties.getCors());
        cors.setAllowedHeaders(corsProperties.getHeaders());
        cors.setAllowedMethods(corsProperties.getMethods());
        cors.addExposedHeader(StringUtils.join(corsProperties.getExposedHeaders(), ", "));

        return cors;
    }
}
