package pl.wsb.issuetracker.issue.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.issue.dto.IssueFiltersDTO;
import pl.wsb.issuetracker.jpa.entity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Component
public class IssueSpecification {

    private static final String REPORTED_BY_FIELD_NAME = "reportedBy";
    private static final String ASSIGNED_TO_FIELD_NAME = "assignedTo";
    private static final String UUID_FIELD_NAME = "uuid";
    private static final String STATUS_FIELD_NAME = "status";
    private static final String SEVERITY_FIELD_NAME = "severity";

    public Specification<Issue> getIssueSpecification(final User user,
                                                      final IssueFiltersDTO filters) {
        return ((root, query, criteriaBuilder) -> {
            final Collection<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(resolveUserField(user)), user));
            addFilters(filters, criteriaBuilder, root, predicates);
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }

    private static String resolveUserField(final User user) {
        return user.getRole().equals(UserRole.REPORTER) ? REPORTED_BY_FIELD_NAME : ASSIGNED_TO_FIELD_NAME;
    }

    private static void addFilters(final IssueFiltersDTO filters,
                                   final CriteriaBuilder criteriaBuilder,
                                   final Root<Issue> root,
                                   final Collection<Predicate> predicates) {
        if (filters != null) {
            addUuidPredicateIfProvided(filters, criteriaBuilder, root, predicates);
            addStatusPredicateIfProvided(filters, criteriaBuilder, root, predicates);
            addSeverityPredicateIfProvided(filters, criteriaBuilder, root, predicates);
        }
    }

    private static void addSeverityPredicateIfProvided(final IssueFiltersDTO filters,
                                                       final CriteriaBuilder criteriaBuilder,
                                                       final Root<Issue> root,
                                                       final Collection<Predicate> predicates) {
        if (filters.getSeverity() == null) return;
        final Predicate severityPredicate = criteriaBuilder.equal(root.get(SEVERITY_FIELD_NAME), IssueSeverity.valueOf(filters.getSeverity()));
        predicates.add(severityPredicate);
    }

    private static void addStatusPredicateIfProvided(final IssueFiltersDTO filters,
                                                     final CriteriaBuilder criteriaBuilder,
                                                     final Root<Issue> root,
                                                     final Collection<Predicate> predicates) {
        if (filters.getStatus() == null) return;
        final Predicate statusPredicate = criteriaBuilder.equal(root.get(STATUS_FIELD_NAME), IssueStatus.valueOf(filters.getStatus()));
        predicates.add(statusPredicate);
    }

    private static void addUuidPredicateIfProvided(final IssueFiltersDTO filters,
                                                   final CriteriaBuilder criteriaBuilder,
                                                   final Root<Issue> root,
                                                   final Collection<Predicate> predicates) {
        if (filters.getUuid() == null) return;
        final Predicate uuidPredicate = criteriaBuilder.equal(root.get(UUID_FIELD_NAME), UUID.fromString(filters.getUuid()));
        predicates.add(uuidPredicate);
    }

}
