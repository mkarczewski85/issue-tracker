package pl.wsb.issuetracker.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.user.UserClient;
import pl.wsb.issuetracker.user.components.*;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserClient {

    private final UserQueryComponent queryComponent;
    private final UserPasswordValidateComponent passwordValidateComponent;
    private final UserCreateComponent userCreateComponent;
    private final UserCredentialsCreateComponent credentialsCreateComponent;
    private final UserPatchComponent patchComponent;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(final String email) {
        return queryComponent.findByEmail(email);
    }

    @Override
    public User createUser(final CreateUserRequestDTO reqDTO) {
        return userCreateComponent.createUser(reqDTO);
    }

    @Override
    public User patchUser(final UUID uuid, final PatchUserRequestDTO reqDTO) {
        return patchComponent.patchUser(uuid, reqDTO);
    }

    @Override
    public User resetUserCredentials(final UUID uuid) {
        final User user = queryComponent.getByUUID(uuid);
        credentialsCreateComponent.recreateUserCredentials(user);
        return user;
    }

    @Override
    public Collection<User> getUsers(UserFiltersDTO filters) {
        return null;
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
