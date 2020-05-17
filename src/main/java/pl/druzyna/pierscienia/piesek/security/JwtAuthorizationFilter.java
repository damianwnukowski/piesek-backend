package pl.druzyna.pierscienia.piesek.security;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.druzyna.pierscienia.piesek.model.repository.UserAccountRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserAccountRepository userAccountRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserAccountRepository userAccountRepository) {
        super(authenticationManager);
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.JWT_HEADER_NAME);

        if (header == null || !header.startsWith(SecurityConstants.JWT_HEADER_VALUE_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.JWT_HEADER_NAME);
        if (token != null) {
            // parse the token.
            String user = JWT.require(SecurityConstants.JWT_ALGORITHM)
                    .build()
                    .verify(token.replace(SecurityConstants.JWT_HEADER_VALUE_PREFIX, ""))
                    .getSubject();

            if (user != null) {
                List<? extends GrantedAuthority> permissions =
                        userAccountRepository.findByEmail(user).getRole().getPermissions();
                return new UsernamePasswordAuthenticationToken(user, null, permissions);
            }
            return null;
        }
        return null;
    }
}
