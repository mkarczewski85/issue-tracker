package pl.wsb.issuetracker.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final UserDeleteComponent deleteComponent;

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(final UserFiltersDTO filters, final int offset, final int limit) {
        return queryComponent.getUsers(filters, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(final String email) {
        return queryComponent.findByEmail(email);
    }

    @Override
    @Transactional
    public User createUser(final CreateUserRequestDTO reqDTO) {
        return userCreateComponent.createUser(reqDTO);
    }

    @Override
    @Transactional
    public User patchUser(final UUID uuid, final PatchUserRequestDTO reqDTO) {
        return patchComponent.patchUser(uuid, reqDTO);
    }

    @Override
    @Transactional
    public User resetUserCredentials(final UUID uuid) {
        final User user = queryComponent.getByUUID(uuid);
        credentialsCreateComponent.recreateUserCredentials(user);
        return user;
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
    @Transactional
    public void deleteUser(final UUID uuid) {
        deleteComponent.deleteUser(uuid);
    }

    @Override
    public boolean isPasswordValid(final UserCredentials userCredentials, final String password) {
        return passwordValidateComponent.isPasswordValid(userCredentials, password);
    }
}
