package pl.wsb.issuetracker.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginPasswordDTO {

    private String login;
    private String password;

}
