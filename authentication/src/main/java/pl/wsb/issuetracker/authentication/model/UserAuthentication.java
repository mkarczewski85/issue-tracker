package pl.wsb.issuetracker.authentication.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private String id;
    private String firstName;
    private String lastName;
    private String department;
    private String location;

    public UserAuthentication(final Object principal,
                              final Object credentials,
                              final Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}

