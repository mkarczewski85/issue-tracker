package pl.wsb.issuetracker.issue.component;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.issue.dto.IssueFiltersDTO;
import pl.wsb.issuetracker.issue.specification.IssueSpecification;
import pl.wsb.issuetracker.jpa.entity.Issue;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.repository.IssueRepository;

import java.util.Collection;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QueryIssueComponent {

    private final IssueRepository issueRepository;
    private final LoggedUserComponent loggedUserComponent;
    private final IssueSpecification specification;

    public Issue getIssue(final UUID uuid) {
        final User user = loggedUserComponent.getLoggedUser();
        final IssueFiltersDTO filter = IssueFiltersDTO.builder().uuid(uuid.toString()).build();
        final Specification<Issue> issueSpecification = specification.getIssueSpecification(user, filter);
        return issueRepository.findOne(issueSpecification)
                .orElseThrow();
    }

    public Collection<Issue> getUserIssues(final IssueFiltersDTO filters) {
        final User user = loggedUserComponent.getLoggedUser();
        final Specification<Issue> issueSpecification = specification.getIssueSpecification(user, filters);
        return issueRepository.findAll(issueSpecification)
                .stream()
                .toList();
    }

}
