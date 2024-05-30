package pl.wsb.issuetracker.issue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.common.exceptions.IllegalOperationException;
import pl.wsb.issuetracker.issue.dto.PatchIssueRequestDTO;
import pl.wsb.issuetracker.jpa.entity.Issue;
import pl.wsb.issuetracker.jpa.entity.IssueSeverity;
import pl.wsb.issuetracker.jpa.entity.IssueStatus;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.repository.IssueRepository;
import pl.wsb.issuetracker.user.UserClient;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatchIssueComponent {

    private final IssueRepository issueRepository;
    private final UserClient userClient;

    public Issue patchIssue(final UUID uuid,
                            final PatchIssueRequestDTO reqDTO) {
        final Issue issue = issueRepository.findByUuid(uuid).orElseThrow();
        patchIssue(issue, reqDTO);
        return issueRepository.save(issue);
    }

    private void patchIssue(final Issue issue,
                            final PatchIssueRequestDTO reqDTO) {
        if (reqDTO.getStatus() != null) {
            updateStatusIfAllowed(issue, IssueStatus.valueOf(reqDTO.getStatus()));
        }
        if (reqDTO.getSeverity() != null) {
            updateSeverity(issue, reqDTO);
        }
        if (reqDTO.getAssignTo() != null) {
            updateAssignedTechnician(issue, reqDTO.getAssignTo());
        }
        issue.setUpdatedAt(LocalDateTime.now());
    }

    private static void updateSeverity(final Issue issue, final PatchIssueRequestDTO reqDTO) {
        issue.setSeverity(IssueSeverity.valueOf(reqDTO.getSeverity()));
    }

    private static void updateStatusIfAllowed(final Issue issue, final IssueStatus newStatus) {
        if (issue.getStatus().canSwitchTo(newStatus)) {
            issue.setStatus(newStatus);
        } else {
            throw new IllegalOperationException("Cannot switch status to:" + newStatus);
        }
    }

    private void updateAssignedTechnician(final Issue issue, final UUID userUuid) {
        final User newAssignment = userClient.getByUUID(userUuid);
        issue.setAssignedTo(newAssignment);
        updateStatusIfAllowed(issue, IssueStatus.ASSIGNED);
    }

}
