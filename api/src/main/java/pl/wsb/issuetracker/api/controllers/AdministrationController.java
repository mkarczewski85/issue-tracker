package pl.wsb.issuetracker.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wsb.issuetracker.administration.AdministrationClient;
import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;

import java.util.UUID;

@RestController
@RequestMapping(AdministrationController.REST_API_BASE_PATH)
@RequiredArgsConstructor
public class AdministrationController {
    static final String REST_API_BASE_PATH = "${rest.prefix}" + "/users";

    private final AdministrationClient administrationClient;

    @PostMapping
    public UserAccountDTO createUserAccount(@RequestBody final CreateUserRequestDTO reqDTO) {
        return administrationClient.createAndNotifyUserAccount(reqDTO);
    }

    @PatchMapping("/{uuid}")
    public UserAccountDTO patchUserAccount(@PathVariable("uuid") final String uuid,
                                           @RequestBody final PatchUserRequestDTO reqDTO) {
        return administrationClient.patchUserAccount(UUID.fromString(uuid), reqDTO);
    }

    @PostMapping("/{uuid}/reset-password")
    public UserAccountDTO resetUserAccountCredentials(@PathVariable("uuid") final String uuid) {
        return administrationClient.resetUserAccountCredentials(UUID.fromString(uuid));
    }
}
