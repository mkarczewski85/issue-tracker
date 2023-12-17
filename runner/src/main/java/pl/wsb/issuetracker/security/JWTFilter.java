package pl.wsb.issuetracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.wsb.issuetracker.authentication.component.JWTUtilityComponent;

import java.io.IOException;
import java.util.Optional;

public class JWTFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private JWTUtilityComponent jwtUtility;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final Optional<String> jwtOpt = parseJWT(request);
        if (jwtOpt.isPresent()) {
            final String jwt = jwtOpt.get();
            if (jwtUtility.isTokenValid(jwt)) {
                final Authentication authentication = jwtUtility.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info(String.format("No valid JWT token found, uri: %s", request.getRequestURI()));
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> parseJWT(final HttpServletRequest request) {
        final String headerAuth = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return Optional.of(headerAuth.substring(7));
        }
        return Optional.empty();
    }
}
