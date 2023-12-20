package pl.wsb.issuetracker.issue.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishIssueCommentRequestDTO {

    @NotBlank
    private String comment;

}
