package pl.druzyna.pierscienia.piesek.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UserAccount userAccount = objectMapper.readValue(request.getInputStream(), UserAccount.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userAccount.getEmail(),
                    userAccount.getPassword(),
                    Collections.emptyList()
            );
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {
        String jwt = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION_TIME_MILLIS))
                .sign(SecurityConstants.JWT_ALGORITHM);
        response.addHeader(SecurityConstants.JWT_HEADER_NAME, SecurityConstants.JWT_HEADER_VALUE_PREFIX + jwt);
    }
}
