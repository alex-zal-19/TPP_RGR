package COM.TPP.RGR.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import COM.TPP.RGR.models.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
