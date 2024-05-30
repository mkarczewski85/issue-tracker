package pl.wsb.issuetracker.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.wsb.issuetracker.jpa.entity.Issue;
import pl.wsb.issuetracker.jpa.entity.IssueComment;

import java.util.Collection;

@Repository
public interface IssueCommentRepository extends CrudRepository<IssueComment, Long>,
        PagingAndSortingRepository<IssueComment, Long>, JpaSpecificationExecutor<IssueComment> {

    Collection<IssueComment> findAllByIssue(Issue issue);

}
