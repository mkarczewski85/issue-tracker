package pl.wsb.issuetracker.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsb.issuetracker.authentication.AuthenticationClient;
import pl.wsb.issuetracker.authentication.component.AuthenticatedUserInfoComponent;
import pl.wsb.issuetracker.authentication.component.UserAuthenticationComponent;
import pl.wsb.issuetracker.authentication.dto.JWTokenDTO;
import pl.wsb.issuetracker.authentication.dto.LoggedUserDTO;
import pl.wsb.issuetracker.authentication.dto.UserLoginPasswordDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationClient {

    private final AuthenticatedUserInfoComponent authenticatedUserInfoComponent;
    private final UserAuthenticationComponent userAuthenticationComponent;

    @Override
    public JWTokenDTO authenticateUser(final UserLoginPasswordDTO credentials) {
        return userAuthenticationComponent.authenticateUser(credentials);
    }

    @Override
    public LoggedUserDTO getLoggedUserDetails() {
        return authenticatedUserInfoComponent.getLoggedUserDetails();
    }

    @Override
    public UUID getLoggedUserUuid() {
        return this.getLoggedUserDetails().getId();
    }
}
