package pl.wsb.issuetracker.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.user.UserClient;
import pl.wsb.issuetracker.user.components.UserPasswordValidateComponent;
import pl.wsb.issuetracker.user.components.UserQueryComponent;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserClient {

    private final UserQueryComponent queryComponent;
    private final UserPasswordValidateComponent passwordValidateComponent;
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(final String email) {
        return queryComponent.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUUID(final UUID uuid) {
        return queryComponent.getByUUID(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public User getRandomWith(final UserRole role) {
        return queryComponent.getRandomWith(role);
    }

    @Override
    public boolean isPasswordValid(final UserCredentials userCredentials, final String password) {
        return passwordValidateComponent.isPasswordValid(userCredentials, password);
    }
}
