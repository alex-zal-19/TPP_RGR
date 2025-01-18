package COM.TPP.RGR.service;

import COM.TPP.RGR.models.Univgroup;
import COM.TPP.RGR.repository.UnivgroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnivgroupService {

    @Autowired
    private UnivgroupRepository univgroupRepository;

    // Получить все университетские группы
    public List<Univgroup> getAllUnivGroups() {
        return univgroupRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    // Найти университетскую группу по ID
    public Optional<Univgroup> findUnivGroupById(int id) {
        return univgroupRepository.findById(id);
    }

    // Сохранить университетскую группу
    public void saveUnivGroup(Univgroup univgroup) {
        univgroupRepository.save(univgroup);
    }

    // Исключение для связанных департаментов (при необходимости)
    public class UnivGroupHasDepartmentException extends RuntimeException {
        public UnivGroupHasDepartmentException(String message) {
            super(message);
        }
    }

    // Обновить данные университетской группы
    public void updateUnivGroup(Univgroup updatedUnivGroup) {
        Univgroup existingUnivGroup = univgroupRepository.findById(updatedUnivGroup.getId())
                .orElseThrow(() -> new IllegalArgumentException("University group not found"));

        existingUnivGroup.setGroupName(updatedUnivGroup.getGroupName());
        existingUnivGroup.setDepartment(updatedUnivGroup.getDepartment());

        univgroupRepository.save(existingUnivGroup);
    }

    // Удалить университетскую группу по ID
    public void deleteUnivGroup(int id) {
        univgroupRepository.deleteById(id);
    }
}
