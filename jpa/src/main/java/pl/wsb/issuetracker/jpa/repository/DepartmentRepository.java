package pl.wsb.issuetracker.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsb.issuetracker.jpa.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
