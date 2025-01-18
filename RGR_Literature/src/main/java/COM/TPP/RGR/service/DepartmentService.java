package COM.TPP.RGR.service;

import COM.TPP.RGR.models.Department;
import COM.TPP.RGR.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Получить все отделы
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll(Sort.by(Sort.Order.asc("departmentId")));
    }

    // Найти отдел по ID
    public Optional<Department> findDepartmentById(int id) {
        return departmentRepository.findById(id);
    }

    // Сохранить отдел
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    // Обновить отдел
    public void updateDepartment(Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(updatedDepartment.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());

        departmentRepository.save(existingDepartment);
    }

    // Удалить отдел
    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }
}
