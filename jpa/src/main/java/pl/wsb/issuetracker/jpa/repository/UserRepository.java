package pl.wsb.issuetracker.jpa.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserRole;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long>,
        PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(final String email);

    Optional<User> findByUuid(final UUID uuid);

    User getByUuid(final UUID uuid);

    @Query(value = "SELECT * FROM user_profiles WHERE role = :role ORDER BY RAND() LIMIT 1", nativeQuery = true)
    User getRandomWithRole(@Param("role") UserRole role);

    boolean existsByEmail(final String email);

}
