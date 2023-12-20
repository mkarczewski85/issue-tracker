package pl.wsb.issuetracker.administration.common;

import pl.wsb.issuetracker.administration.dto.DepartmentDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
import pl.wsb.issuetracker.administration.dto.UserAccountDisplayDTO;
import pl.wsb.issuetracker.jpa.entity.Department;
import pl.wsb.issuetracker.jpa.entity.User;

public final class MappingHelper {

    private MappingHelper() {
    }

    public static UserAccountDTO toUserAccountDTO(final User user) {
        return UserAccountDTO.builder()
                .uuid(user.getUuid().toString())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .departmentName(user.getDepartment().getName())
                .departmentLocation(user.getDepartment().getLocation())
                .role(user.getRole().name())
                .build();
    }

    public static UserAccountDisplayDTO toUserAccountDisplayDTO(final User user) {
        return UserAccountDisplayDTO.builder()
                .uuid(user.getUuid().toString())
                .displayName(user.getFirstName() + " " + user.getLastName())
                .departmentName(user.getDepartment().getName())
                .role(user.getRole().name())
                .build();
    }

    public static DepartmentDTO toDepartmentDTO(final Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .location(department.getLocation())
                .build();
    }
}
