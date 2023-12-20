package pl.wsb.issuetracker.issue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportIssueRequestDTO {

    @NotBlank
    @Size(max = 150)
    private String summary;
    @NotBlank
    @Size(max = 2500)
    private String description;
    @NotBlank
    private String severity;

}
