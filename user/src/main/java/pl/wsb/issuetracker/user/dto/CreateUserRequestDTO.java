package pl.wsb.issuetracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {

    private String firstName;
    private String lastName;
    private long departmentId;
    private String email;
    private String role;

}
