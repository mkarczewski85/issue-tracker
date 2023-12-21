package pl.wsb.issuetracker.user;

import org.springframework.data.domain.Page;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.Optional;
import java.util.UUID;

public interface UserClient {

    Page<User> getUsers(UserFiltersDTO filters, int offset, int limit);

    Optional<User> findByEmail(String email);

    User createUser(CreateUserRequestDTO reqDTO);

    User patchUser(UUID uuid, PatchUserRequestDTO reqDTO);

    User resetUserCredentials(UUID uuid);

    User getByUUID(UUID uuid);

    User getRandomWith(UserRole role);

    void deleteUser(UUID uuid);

    boolean isPasswordValid(UserCredentials userCredentials, String password);

}
