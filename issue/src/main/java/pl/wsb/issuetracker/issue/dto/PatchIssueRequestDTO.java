package pl.wsb.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatchIssueRequestDTO {

    private String status;
    private String severity;
    private UUID assignTo;

}
