package pl.wsb.issuetracker.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wsb.issuetracker.authentication.AuthenticationClient;
import pl.wsb.issuetracker.authentication.dto.JWTokenDTO;
import pl.wsb.issuetracker.authentication.dto.LoggedUserDTO;
import pl.wsb.issuetracker.authentication.dto.UserLoginPasswordDTO;

@RestController
@Validated
@RequestMapping(AuthenticationController.REST_API_BASE_PATH)
@RequiredArgsConstructor
public class AuthenticationController {

    static final String REST_API_BASE_PATH = "${rest.prefix}";
    private static final String AUTHENTICATE_PATH = "${rest.public.path}/authenticate";
    private static final String ME_PATH = "${rest.secured.path}/me";

    private final AuthenticationClient authenticationClient;

    @PostMapping(AUTHENTICATE_PATH)
    public ResponseEntity<JWTokenDTO> authenticate(@RequestBody @NotNull @Valid final UserLoginPasswordDTO loginPasswordDto) {
        final JWTokenDTO jwToken = authenticationClient.authenticateUser(loginPasswordDto);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwToken.getToken());
        return new ResponseEntity<>(jwToken, httpHeaders, HttpStatus.OK);
    }

    @GetMapping(ME_PATH)
    public LoggedUserDTO getLoggedUser() {
        return authenticationClient.getLoggedUserDetails();
    }

}
