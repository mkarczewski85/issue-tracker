package pl.wsb.issuetracker.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JWTokenDTO {

    private String token;
    private Long expiresIn;
    private LoggedUserDTO userInfo;

}
