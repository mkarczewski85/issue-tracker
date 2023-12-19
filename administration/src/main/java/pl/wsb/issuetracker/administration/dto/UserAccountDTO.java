package pl.wsb.issuetracker.administration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserAccountDTO {

    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String departmentName;
    private String departmentLocation;
}
