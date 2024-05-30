package pl.wsb.issuetracker.issue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.issue.dto.PublishIssueCommentRequestDTO;
import pl.wsb.issuetracker.jpa.entity.Issue;
import pl.wsb.issuetracker.jpa.entity.IssueComment;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.repository.IssueCommentRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PublishIssueCommentComponent {

    private final QueryIssueComponent queryIssueComponent;
    private final IssueCommentRepository commentRepository;
    private final LoggedUserComponent loggedUserComponent;

    public IssueComment publishIssueComment(final UUID uuid,
                                            final PublishIssueCommentRequestDTO reqDTO) {
        final Issue issue = queryIssueComponent.getIssue(uuid);
        final User user = loggedUserComponent.getLoggedUser();
        final IssueComment comment = createComment(issue, user, reqDTO);
        return commentRepository.save(comment);
    }

    private static IssueComment createComment(final Issue issue,
                                              final User user,
                                              final PublishIssueCommentRequestDTO reqDTO) {
        return IssueComment.builder()
                .publishedBy(user)
                .comment(reqDTO.getComment())
                .issue(issue)
                .build();
    }

}
