package pl.wsb.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class IssueCommentDTO {

    private String comment;
    private String publishedBy;
    private LocalDateTime publishedAt;

}
