package pl.wsb.issuetracker.authentication.component;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.authentication.dto.JWTokenDTO;
import pl.wsb.issuetracker.authentication.dto.LoggedUserDTO;
import pl.wsb.issuetracker.authentication.dto.UserLoginPasswordDTO;
import pl.wsb.issuetracker.authentication.model.UserAuthentication;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.user.UserClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAuthenticationComponent {

    private static final String ROLE_PREFIX = "ROLE_";

    private final UserClient userClient;
    private final JWTUtilityComponent jwtUtility;

    public JWTokenDTO authenticateUser(final UserLoginPasswordDTO credentials) {
        final String login = credentials.getLogin();
        final String password = credentials.getPassword();
        final User user = userClient.findByEmail(login)
                .orElseThrow(() -> new BadCredentialsException("Error for user: " + login));
        if (user.isActive() && userClient.isPasswordValid(user.getUserCredentials(), password)) {
            UserAuthentication authentication = createAuthentication(user, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String jwToken = jwtUtility.createJWT(authentication);
            final LoggedUserDTO userDto = toLoggedUserDTO(user);
            return JWTokenDTO.builder()
                    .token(jwToken)
                    .expiresIn(jwtUtility.getTokenValidityInSeconds())
                    .userInfo(userDto)
                    .build();
        } else {
            throw new BadCredentialsException("Error for user: " + login);
        }
    }

    private UserAuthentication createAuthentication(final User user, final String password) {
        final UserAuthentication authenticationToken = new UserAuthentication(user.getEmail(),
                password, List.of(new SimpleGrantedAuthority(ROLE_PREFIX.concat(user.getRole().name()))));
        authenticationToken.setId(user.getUuid().toString());
        authenticationToken.setFirstName(user.getFirstName());
        authenticationToken.setLastName(user.getLastName());
        return authenticationToken;
    }

    private static LoggedUserDTO toLoggedUserDTO(final User user) {
        return LoggedUserDTO.builder()
                .id(user.getUuid())
                .username(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .department(user.getDepartment().getName())
                .location(user.getDepartment().getLocation())
                .build();
    }

}
