package pl.wsb.issuetracker.issue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.authentication.AuthenticationClient;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.user.UserClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoggedUserComponent {

    private final UserClient userClient;
    private final AuthenticationClient authenticationClient;

    public User getLoggedUser() {
        final UUID uuid = authenticationClient.getLoggedUserUuid();
        return userClient.getByUUID(uuid);
    }

}
