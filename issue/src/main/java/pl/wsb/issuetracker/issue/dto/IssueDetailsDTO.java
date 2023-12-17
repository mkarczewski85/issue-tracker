package pl.wsb.issuetracker.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class IssueDetailsDTO {

    private UUID uuid;
    private String summary;
    private String description;
    private String severity;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String assignedTo;

}
