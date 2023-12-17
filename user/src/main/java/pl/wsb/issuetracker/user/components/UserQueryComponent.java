package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.jpa.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserQueryComponent {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public User getByUUID(final UUID uuid) {
        return userRepository.findByUuid(uuid).orElseThrow();
    }

    public User getRandomWith(final UserRole userRole) {
        return userRepository.getRandomWithRole(userRole);
    }

}
