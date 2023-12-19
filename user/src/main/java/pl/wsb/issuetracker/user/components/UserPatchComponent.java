package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.entity.Department;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.repository.DepartmentRepository;
import pl.wsb.issuetracker.user.dto.PatchUserRequestDTO;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPatchComponent {

    private final UserQueryComponent queryComponent;
    private final DepartmentRepository departmentRepository;

    public User patchUser(final UUID uuid, final PatchUserRequestDTO reqDTO) {
        final User user = queryComponent.getByUUID(uuid);
        return patchUser(user, reqDTO);
    }

    private User patchUser(final User user, final PatchUserRequestDTO reqDTO) {
        updateFirstName(user, reqDTO);
        updateLastName(user, reqDTO);
        updateEmail(user, reqDTO);
        updateDepartment(user, reqDTO);
        updateAccountStatus(user, reqDTO);
        return user;
    }

    private static void updateFirstName(final User user, final PatchUserRequestDTO reqDTO) {
        if (reqDTO.getFirstName() == null) return;
        user.setFirstName(reqDTO.getFirstName());
    }

    private static void updateLastName(final User user, final PatchUserRequestDTO reqDTO) {
        if (reqDTO.getLastName() == null) return;
        user.setLastName(reqDTO.getLastName());
    }

    private static void updateEmail(final User user, final PatchUserRequestDTO reqDTO) {
        if (reqDTO.getEmail() == null) return;
        user.setEmail(reqDTO.getEmail());
    }

    private void updateDepartment(final User user, final PatchUserRequestDTO reqDTO) {
        if (reqDTO.getDepartmentId() == null) return;
        final Department department = departmentRepository.findById(reqDTO.getDepartmentId()).orElseThrow();
        user.setDepartment(department);
    }

    private static void updateAccountStatus(final User user, final PatchUserRequestDTO reqDTO) {
        if (reqDTO.getIsActive() == null) return;
        user.setActive(reqDTO.getIsActive());
    }

}
