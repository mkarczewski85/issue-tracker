package pl.wsb.issuetracker.user;

import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;
import pl.wsb.issuetracker.jpa.entity.UserRole;

import java.util.Optional;
import java.util.UUID;

public interface UserClient {

    Optional<User> findByEmail(String email);

    User getByUUID(UUID uuid);

    User getRandomWith(UserRole role);

    boolean isPasswordValid(UserCredentials userCredentials, String password);

}
