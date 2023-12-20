package pl.wsb.issuetracker.administration;

import pl.wsb.issuetracker.administration.dto.DepartmentDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDisplayDTO;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.Collection;
import java.util.UUID;

public interface AdministrationClient {

    UserAccountDTO createAndNotifyUserAccount(CreateUserRequestDTO reqDTO);

    UserAccountDTO resetUserAccountCredentials(UUID uuid);

    UserAccountDTO patchUserAccount(UUID uuid, PatchUserRequestDTO reqDTO);

    PageWrapperDTO<UserAccountDisplayDTO> getUserAccounts(UserFiltersDTO filters, int offset, int limit);

    UserAccountDTO getUserAccount(UUID uuid);

    Collection<DepartmentDTO> getAllDepartments();



}
