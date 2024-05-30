package pl.wsb.issuetracker.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wsb.issuetracker.common.validators.ValueOfEnum;
import pl.wsb.issuetracker.jpa.entity.UserRole;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private long departmentId;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @ValueOfEnum(enumClass = UserRole.class)
    private String role;

}
