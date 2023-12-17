package pl.wsb.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportIssueRequestDTO {

    private String summary;
    private String description;
    private String severity;

}
