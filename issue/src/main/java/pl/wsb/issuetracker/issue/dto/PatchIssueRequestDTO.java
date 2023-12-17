package pl.wsb.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchIssueRequestDTO {

    private String status;
    private String severity;
    private UUID assignTo;

}
