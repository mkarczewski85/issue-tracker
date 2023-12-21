package pl.wsb.issuetracker.issue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.repository.IssueRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteIssueComponent {

    private final IssueRepository issueRepository;

    public void deleteIssue(final UUID uuid) {
        issueRepository.deleteByUuid(uuid);
    }

}
