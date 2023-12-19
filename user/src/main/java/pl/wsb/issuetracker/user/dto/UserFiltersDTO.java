package pl.wsb.issuetracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFiltersDTO {

    private String uuid;
    private String namePhrase;
    private String emailPhrase;
    private String userRole;

}
