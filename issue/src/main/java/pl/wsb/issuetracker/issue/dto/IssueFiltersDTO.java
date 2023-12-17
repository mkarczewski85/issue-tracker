package pl.wsb.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IssueFiltersDTO {

    private String uuid;
    private String status;
    private String severity;

}
