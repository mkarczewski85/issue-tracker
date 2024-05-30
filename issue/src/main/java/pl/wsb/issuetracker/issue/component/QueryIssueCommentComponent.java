package pl.wsb.issuetracker.issue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.entity.Issue;
import pl.wsb.issuetracker.jpa.entity.IssueComment;
import pl.wsb.issuetracker.jpa.repository.IssueCommentRepository;

import java.util.Collection;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QueryIssueCommentComponent {

    private final IssueCommentRepository issueCommentRepository;
    private final QueryIssueComponent queryIssueComponent;

    public Collection<IssueComment> getIssueComments(final UUID uuid) {
        final Issue issue = queryIssueComponent.getIssue(uuid);
        return issueCommentRepository.findAllByIssue(issue);
    }

}
