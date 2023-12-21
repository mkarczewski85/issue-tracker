package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.repository.UserRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserDeleteComponent {

    private final UserRepository userRepository;

    public void deleteUser(final UUID uuid) {
        userRepository.deleteByUuid(uuid);
    }

}
