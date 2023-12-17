package pl.wsb.issuetracker.authentication.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class LoggedUserDTO {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String department;
    private String location;

}
