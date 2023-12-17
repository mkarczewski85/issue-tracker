package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserCreateComponent {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return null;
    }

}
