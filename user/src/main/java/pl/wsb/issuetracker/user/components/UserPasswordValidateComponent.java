package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class UserPasswordValidateComponent {

    private final UserCredentialsCreateComponent credentialsCreateComponent;

    public boolean isPasswordValid(final UserCredentials userCredentials,
                                   final String rawPassword) {
        byte[] bytesOfSalt = Base64.getDecoder().decode(userCredentials.getSalt());
        return credentialsCreateComponent.generateSaltedHash(rawPassword, bytesOfSalt)
                .equals(userCredentials.getPasswordHash());
    }
}
