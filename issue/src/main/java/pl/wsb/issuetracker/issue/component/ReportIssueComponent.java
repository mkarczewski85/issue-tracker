package pl.wsb.issuetracker.issue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.authentication.AuthenticationClient;
import pl.wsb.issuetracker.authentication.dto.LoggedUserDTO;
import pl.wsb.issuetracker.issue.dto.ReportIssueRequestDTO;
import pl.wsb.issuetracker.jpa.entity.Issue;
import pl.wsb.issuetracker.jpa.entity.IssueSeverity;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.jpa.repository.IssueRepository;
import pl.wsb.issuetracker.user.UserClient;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ReportIssueComponent {

    private final IssueRepository issueRepository;
    private final AuthenticationClient authenticationClient;
    private final UserClient userClient;

    public Issue reportIssue(final ReportIssueRequestDTO reqDTO) {
        final Issue issue = createIssue(reqDTO);
        return issueRepository.save(issue);
    }

    private Issue createIssue(final ReportIssueRequestDTO reqDTO) {
        return Issue.builder()
                .summary(reqDTO.getSummary())
                .description(reqDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .reportedBy(getLoggedReporter())
                .assignedTo(getRandomTechnician())
                .severity(IssueSeverity.valueOf(reqDTO.getSeverity()))
                .build();
    }

    private User getLoggedReporter() {
        final LoggedUserDTO loggedUserDetails = authenticationClient.getLoggedUserDetails();
        return userClient.getByUUID(loggedUserDetails.getId());
    }

    private User getRandomTechnician() {
        return userClient.getRandomWith(UserRole.TECHNICIAN);
    }

}
