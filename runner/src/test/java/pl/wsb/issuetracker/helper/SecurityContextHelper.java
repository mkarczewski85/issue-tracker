package pl.wsb.issuetracker.helper;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.wsb.issuetracker.authentication.model.UserAuthentication;

import java.util.List;
import java.util.Map;

public class SecurityContextHelper {

    public static final Map<String, TestAuthenticationData> AUTHENTICATION_DATA_MAP = Map.of(
            "REPORTER", new TestAuthenticationData("57679399-2b5f-470b-a512-acc2e6940feb", "Ewa", "Kaczmarek", "password", "REPORTER"),
            "TECHNICIAN", new TestAuthenticationData("cc4fa18d-8d87-4b4d-9128-1d25bbe561d1", "Jan", "Nowak", "password", "TECHNICIAN"),
            "ADMINISTRATOR", new TestAuthenticationData("1116041a-40b7-4419-848a-78e8c9d5b09f", "Jan", "Kowalski", "password", "ADMINISTRATOR")
    );


    public static void loginAs(String role) {
        UserAuthentication authentication = createAuthentication(AUTHENTICATION_DATA_MAP.get(role));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static UserAuthentication createAuthentication(TestAuthenticationData user) {
        UserAuthentication authenticationToken = new UserAuthentication("",
                user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_".concat(user.getRole()))));
        authenticationToken.setId(user.getId());
        authenticationToken.setFirstName(user.getFirstName());
        authenticationToken.setLastName(user.getLastName());
        return authenticationToken;
    }

}
