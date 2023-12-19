package pl.wsb.issuetracker.administration.common;

import pl.wsb.issuetracker.administration.dto.UserAccountDTO;
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
}
