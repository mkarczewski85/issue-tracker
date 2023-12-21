package pl.wsb.issuetracker.issue.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishIssueCommentRequestDTO {

    @NotBlank
    @Size(max = 2000)
    private String comment;

}
