package pl.wsb.issuetracker.administration;

import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;

import java.util.Collection;
import java.util.UUID;

public interface AdministrationClient {

    UserAccountDTO createAndNotifyUserAccount(CreateUserRequestDTO reqDTO);

    UserAccountDTO resetUserAccountCredentials(UUID uuid);

    UserAccountDTO patchUserAccount(UUID uuid, PatchUserRequestDTO reqDTO);

    Collection<UserAccountDTO> getUserAccounts();

}
