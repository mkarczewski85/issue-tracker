package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.common.exceptions.NotFoundException;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.jpa.repository.UserRepository;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserQueryComponent {

    private final UserRepository userRepository;
    private final UserSpecificationComponent specificationComponent;

    public Page<User> getUsers(final UserFiltersDTO filters, final int offset, final int limit) {
        int pageNo = (limit + offset) / limit;
        final PageRequest pageRequest = PageRequest.of(--pageNo, limit, Sort.by(Sort.Direction.ASC, "lastName"));
        final Specification<User> specification = specificationComponent.getUserSpecification(filters);
        return userRepository.findAll(specification, pageRequest);
    }

    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmailAndIsActive(email, true);
    }

    public User getByUUID(final UUID uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Unable to find user"));
    }

    public User getRandomWith(final UserRole userRole) {
        return userRepository.getRandomWithRole(userRole.name());
    }

}
