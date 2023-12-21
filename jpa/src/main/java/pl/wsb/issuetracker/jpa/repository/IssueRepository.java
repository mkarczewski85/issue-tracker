package pl.wsb.issuetracker.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.wsb.issuetracker.jpa.entity.Issue;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Long>, PagingAndSortingRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    Optional<Issue> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

}
