package pl.wsb.issuetracker.administration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.wsb.issuetracker.administration.AdministrationClient;
import pl.wsb.issuetracker.administration.common.MappingHelper;
import pl.wsb.issuetracker.administration.component.AccountOwnerNotificationComponent;
import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDisplayDTO;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.user.UserClient;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdministrationService implements AdministrationClient {

    private final UserClient userClient;
    private final AccountOwnerNotificationComponent notificationComponent;

    @Override
    public UserAccountDTO createAndNotifyUserAccount(final CreateUserRequestDTO reqDTO) {
        final User user = userClient.createUser(reqDTO);
        notificationComponent.notifyAccountCreated(user);
        return MappingHelper.toUserAccountDTO(user);
    }

    @Override
    public UserAccountDTO resetUserAccountCredentials(final UUID uuid) {
        final User user = userClient.resetUserCredentials(uuid);
        notificationComponent.notifyCredentialsReset(user);
        return MappingHelper.toUserAccountDTO(user);
    }

    @Override
    public UserAccountDTO patchUserAccount(final UUID uuid, final PatchUserRequestDTO reqDTO) {
        final User user = userClient.patchUser(uuid, reqDTO);
        return MappingHelper.toUserAccountDTO(user);
    }

    @Override
    public PageWrapperDTO<UserAccountDisplayDTO> getUserAccounts(final UserFiltersDTO filters,
                                                                 final int offset,
                                                                 final int limit) {
        final Page<UserAccountDisplayDTO> page = userClient.getUsers(filters, offset, limit).map(MappingHelper::toUserAccountDisplayDTO);
        return PageWrapperDTO.from(page);
    }

    @Override
    public UserAccountDTO getUserAccount(final UUID uuid) {
        return MappingHelper.toUserAccountDTO(userClient.getByUUID(uuid));
    }

}
