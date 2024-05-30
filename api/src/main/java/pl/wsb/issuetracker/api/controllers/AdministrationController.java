package pl.wsb.issuetracker.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wsb.issuetracker.administration.AdministrationClient;
import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDisplayDTO;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.UUID;

@RestController
@RequestMapping(AdministrationController.REST_API_BASE_PATH)
@RequiredArgsConstructor
@Validated
public class AdministrationController {
    static final String REST_API_BASE_PATH = "${rest.prefix}${rest.secured.path}" + "/users";

    private final AdministrationClient administrationClient;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public PageWrapperDTO<UserAccountDisplayDTO> getPagedUserAccounts(@RequestParam(defaultValue = "0") @Min(0) final int offset,
                                                                      @RequestParam(defaultValue = "20") @Min(20) final int limit,
                                                                      @RequestBody(required = false) final UserFiltersDTO filters) {
        return administrationClient.getUserAccounts(filters, offset, limit);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserAccountDTO getUserAccount(@PathVariable("uuid") final String uuid) {
        return administrationClient.getUserAccount(UUID.fromString(uuid));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserAccountDTO createUserAccount(@RequestBody @Valid @NotNull final CreateUserRequestDTO reqDTO) {
        return administrationClient.createAndNotifyUserAccount(reqDTO);
    }

    @PatchMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserAccountDTO patchUserAccount(@PathVariable("uuid") final String uuid,
                                           @RequestBody final PatchUserRequestDTO reqDTO) {
        return administrationClient.patchUserAccount(UUID.fromString(uuid), reqDTO);
    }

    @PostMapping("/{uuid}/reset-password")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserAccountDTO resetUserAccountCredentials(@PathVariable("uuid") final String uuid) {
        return administrationClient.resetUserAccountCredentials(UUID.fromString(uuid));
    }
}
