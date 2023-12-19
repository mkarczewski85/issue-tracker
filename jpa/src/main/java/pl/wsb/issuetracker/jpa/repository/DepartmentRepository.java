package pl.wsb.issuetracker.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wsb.issuetracker.jpa.entity.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
