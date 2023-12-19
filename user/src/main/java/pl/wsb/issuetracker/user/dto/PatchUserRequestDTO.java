package pl.wsb.issuetracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchUserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;
    private Boolean isActive;

}
