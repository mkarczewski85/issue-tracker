package pl.wsb.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class IssueDisplayDTO {

    private String uuid;
    private String summary;
    private String severity;
    private String status;
    private LocalDateTime createdAt;

}
