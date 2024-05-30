package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.common.exceptions.AlreadyExistsException;
import pl.wsb.issuetracker.common.exceptions.NotFoundException;
import pl.wsb.issuetracker.jpa.entity.Department;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.jpa.repository.DepartmentRepository;
import pl.wsb.issuetracker.jpa.repository.UserRepository;
import pl.wsb.issuetracker.user.dto.CreateUserRequestDTO;

@Component
@RequiredArgsConstructor
public class UserCreateComponent {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final UserCredentialsCreateComponent credentialsCreateComponent;

    public User createUser(final CreateUserRequestDTO reqDTO) {
        if (userRepository.existsByEmail(reqDTO.getEmail())) {
            throw new AlreadyExistsException("User with given email address already exists");
        }
        final Department department = findDepartment(reqDTO.getDepartmentId());
        final User user = createUser(reqDTO, department);
        final UserCredentials userCredentials = credentialsCreateComponent.createUserCredentials(user);
        user.setUserCredentials(userCredentials);
        return userRepository.save(user);
    }

    private static User createUser(final CreateUserRequestDTO reqDTO,
                                   final Department department) {
        return User.builder()
                .firstName(reqDTO.getFirstName())
                .lastName(reqDTO.getLastName())
                .role(UserRole.valueOf(reqDTO.getRole()))
                .email(reqDTO.getEmail())
                .department(department)
                .build();
    }

    private Department findDepartment(final long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find department"));
    }

}
