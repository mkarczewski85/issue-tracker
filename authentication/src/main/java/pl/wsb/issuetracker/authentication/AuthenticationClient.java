package pl.wsb.issuetracker.authentication;

import pl.wsb.issuetracker.authentication.dto.JWTokenDTO;
import pl.wsb.issuetracker.authentication.dto.LoggedUserDTO;
import pl.wsb.issuetracker.authentication.dto.UserLoginPasswordDTO;

import java.util.UUID;

public interface AuthenticationClient {

    JWTokenDTO authenticateUser(UserLoginPasswordDTO credentials);

    LoggedUserDTO getLoggedUserDetails();

    UUID getLoggedUserUuid();

}
