package pl.wsb.issuetracker.administration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDisplayDTO;
import pl.wsb.issuetracker.api.controllers.AdministrationController;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.helper.SecurityContextHelper;
import pl.wsb.issuetracker.user.UserClient;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.wsb.issuetracker.helper.TestDataHelper.ADMINISTRATOR;
import static pl.wsb.issuetracker.helper.TestDataHelper.REPORTER;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class AdministrationApiIntegrationTest {

    public static final CreateUserRequestDTO CREATE_USER_REQ_DTO = CreateUserRequestDTO.builder()
            .email("marcin.kwatkowski@company.com")
            .departmentId(2L)
            .firstName("Marcin")
            .lastName("Kwiatkowski")
            .role(REPORTER)
            .build();

    public static final PatchUserRequestDTO PATCH_USER_REQ_DTO = PatchUserRequestDTO.builder()
            .email("szymon.kwatkowski@company.com")
            .firstName("Szymon")
            .build();

    public static final PatchUserRequestDTO DEACTIVATE_PATCH_USER_REQ_DTO = PatchUserRequestDTO.builder()
            .isActive(false)
            .build();
    public static final UserFiltersDTO ONLY_ACTIVE_FILTER = UserFiltersDTO.builder()
            .isActive(true)
            .build();

    @Autowired
    private AdministrationController tested;
    @Autowired
    private UserClient userClient;

    private UUID userUuid;

    @AfterEach
    public void cleanup() {
        if (userUuid != null) {
            userClient.deleteUser(userUuid);
        }
    }

    @BeforeEach
    public void loginAsAdministrator() {
        SecurityContextHelper.loginAs(ADMINISTRATOR);
    }

    @Test
    public void shouldCreateNewUserAccount() {

        // when 1
        UserAccountDTO actual1 = tested.createUserAccount(CREATE_USER_REQ_DTO);

        // then 1
        assertThat(actual1.getUuid()).isNotBlank();
        assertThat(actual1.getRole()).isEqualTo(CREATE_USER_REQ_DTO.getRole());
        assertThat(actual1.getEmail()).isEqualTo(CREATE_USER_REQ_DTO.getEmail());
        assertThat(actual1.getFirstName()).isEqualTo(CREATE_USER_REQ_DTO.getFirstName());
        assertThat(actual1.getLastName()).isEqualTo(CREATE_USER_REQ_DTO.getLastName());

        // when 2
        PageWrapperDTO<UserAccountDisplayDTO> actual2 = tested.getPagedUserAccounts(0, 20, null);

        // then 2
        assertThat(actual2.getTotalCount()).isEqualTo(4);

        // finally
        this.userUuid = UUID.fromString(actual1.getUuid());
    }

    @Test
    public void shouldPatchUserAccountData() {

        // given
        UserAccountDTO userAccount = tested.createUserAccount(CREATE_USER_REQ_DTO);

        // when
        UserAccountDTO actual = tested.patchUserAccount(userAccount.getUuid(), PATCH_USER_REQ_DTO);

        // then
        assertThat(actual.getFirstName()).isEqualTo(PATCH_USER_REQ_DTO.getFirstName());
        assertThat(actual.getEmail()).isEqualTo(PATCH_USER_REQ_DTO.getEmail());

        // finally
        this.userUuid = UUID.fromString(actual.getUuid());
    }

    @Test
    public void shouldDeactivateUserAccount() {
        // given
        UserAccountDTO userAccount = tested.createUserAccount(CREATE_USER_REQ_DTO);

        // when
        tested.patchUserAccount(userAccount.getUuid(), DEACTIVATE_PATCH_USER_REQ_DTO);

        PageWrapperDTO<UserAccountDisplayDTO> actual = tested.getPagedUserAccounts(0, 20, ONLY_ACTIVE_FILTER);

        // then
        assertThat(actual.getTotalCount()).isEqualTo(3);
    }

}
