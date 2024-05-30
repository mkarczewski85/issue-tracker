package pl.wsb.issuetracker.user.components;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserRole;
import pl.wsb.issuetracker.user.dto.UserFiltersDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Component
public class UserSpecificationComponent {

    private static final String FIRST_NAME_FIELD_NAME = "firstName";
    private static final String LAST_NAME_FIELD_NAME = "lastName";
    private static final String EMAIL_FIELD_NAME = "email";
    public static final String UUID_FIELD_NAME = "uuid";
    public static final String ROLE_FIELD_NAME = "role";
    public static final String IS_ACTIVE_FIELD_NAME = "isActive";

    public Specification<User> getUserSpecification(UserFiltersDTO filters) {
        return ((root, query, criteriaBuilder) -> {
            final Collection<Predicate> predicates = new ArrayList<>();
            addFilters(filters, criteriaBuilder, root, predicates);
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        });
    }

    private static void addFilters(final UserFiltersDTO filters,
                                   final CriteriaBuilder criteriaBuilder,
                                   final Root<User> root,
                                   final Collection<Predicate> predicates) {
        if (filters == null) return;
        addNamePhrasePredicateIfProvided(filters, criteriaBuilder, root, predicates);
        addEmailPhrasePredicateIfProvided(filters, criteriaBuilder, root, predicates);
        addUuidPredicateIfProvided(filters, criteriaBuilder, root, predicates);
        addRolePredicateIfProvided(filters, criteriaBuilder, root, predicates);
        addIsActivePredicateIfProvided(filters, criteriaBuilder, root, predicates);
    }

    private static void addNamePhrasePredicateIfProvided(final UserFiltersDTO filters,
                                                         final CriteriaBuilder criteriaBuilder,
                                                         final Root<User> root,
                                                         final Collection<Predicate> predicates) {
        if (filters.getNamePhrase() == null) return;
        final String searchPhraseLike = addWildcards(filters.getNamePhrase());
        final Predicate firstNameLikePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get(FIRST_NAME_FIELD_NAME)), searchPhraseLike);
        final Predicate lastNameLikePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get(LAST_NAME_FIELD_NAME)), searchPhraseLike);
        predicates.add(criteriaBuilder.or(firstNameLikePredicate, lastNameLikePredicate));
    }

    private static void addEmailPhrasePredicateIfProvided(final UserFiltersDTO filters,
                                                          final CriteriaBuilder criteriaBuilder,
                                                          final Root<User> root,
                                                          final Collection<Predicate> predicates) {
        if (filters.getEmailPhrase() == null) return;
        final String searchPhraseLike = addWildcards(filters.getEmailPhrase());
        final Predicate emailLikePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get(EMAIL_FIELD_NAME)), searchPhraseLike);
        predicates.add(emailLikePredicate);
    }

    private static void addUuidPredicateIfProvided(final UserFiltersDTO filters,
                                                   final CriteriaBuilder criteriaBuilder,
                                                   final Root<User> root,
                                                   final Collection<Predicate> predicates) {
        if (filters.getUuid() == null) return;
        final Predicate uuidEqualPredicate = criteriaBuilder.equal(root.get(UUID_FIELD_NAME), UUID.fromString(filters.getUuid()));
        predicates.add(uuidEqualPredicate);
    }

    private static void addRolePredicateIfProvided(final UserFiltersDTO filters,
                                                   final CriteriaBuilder criteriaBuilder,
                                                   final Root<User> root,
                                                   final Collection<Predicate> predicates) {
        if (filters.getUserRole() == null) return;
        final Predicate userRoleEqualPredicate = criteriaBuilder.equal(root.get(ROLE_FIELD_NAME), UserRole.valueOf(filters.getUserRole()));
        predicates.add(userRoleEqualPredicate);
    }

    private static void addIsActivePredicateIfProvided(final UserFiltersDTO filters,
                                                       final CriteriaBuilder criteriaBuilder,
                                                       final Root<User> root,
                                                       final Collection<Predicate> predicates) {
        if (filters.getIsActive() == null) return;
        final Predicate userRoleEqualPredicate = criteriaBuilder.equal(root.get(IS_ACTIVE_FIELD_NAME), filters.getIsActive());
        predicates.add(userRoleEqualPredicate);
    }

    private static String addWildcards(final String searchPhrase) {
        return "%" + searchPhrase.toLowerCase() + "%";
    }

}
