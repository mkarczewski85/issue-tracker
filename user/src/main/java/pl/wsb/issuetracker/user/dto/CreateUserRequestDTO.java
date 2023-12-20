package pl.wsb.issuetracker.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private long departmentId;
    @NotBlank
    private String email;
    @NotBlank
    private String role;

}
