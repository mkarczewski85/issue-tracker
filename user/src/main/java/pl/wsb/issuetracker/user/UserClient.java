package pl.wsb.issuetracker.user;

import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserClient {

    Optional<User> findByEmail(String email);

    User createUser(CreateUserRequestDTO reqDTO);

    User patchUser(UUID uuid, PatchUserRequestDTO reqDTO);

    User resetUserCredentials(UUID uuid);

    Collection<User> getUsers(UserFiltersDTO filters);

    User getByUUID(UUID uuid);

    User getRandomWith(UserRole role);

    boolean isPasswordValid(UserCredentials userCredentials, String password);

}
