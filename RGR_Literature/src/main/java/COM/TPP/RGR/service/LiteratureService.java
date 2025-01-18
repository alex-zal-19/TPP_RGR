package COM.TPP.RGR.service;

import COM.TPP.RGR.models.Literature;
import COM.TPP.RGR.repository.LiteratureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiteratureService {

    @Autowired
    private LiteratureRepository literatureRepository;

    // Получить всю литературу
    public List<Literature> getAllLiteratures() {
        return literatureRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    // Найти литературное произведение по ID
    public Optional<Literature> findLiteratureById(int id) {
        return literatureRepository.findById(id);
    }

    // Сохранить литературное произведение
    public void saveLiterature(Literature literature) {
        literatureRepository.save(literature);
    }

    // Обновить данные литературного произведения
    public void updateLiterature(Literature updatedLiterature) {
        Literature existingLiterature = literatureRepository.findById(updatedLiterature.getId())
                .orElseThrow(() -> new IllegalArgumentException("Literature not found"));

        existingLiterature.setLiteratureName(updatedLiterature.getLiteratureName());
        existingLiterature.setDepartment(updatedLiterature.getDepartment());
        existingLiterature.setUnivGroup(updatedLiterature.getUnivGroup());

        literatureRepository.save(existingLiterature);
    }

    // Удалить литературное произведение по ID
    public void deleteLiterature(int id) {
        literatureRepository.deleteById(id);
    }
}
