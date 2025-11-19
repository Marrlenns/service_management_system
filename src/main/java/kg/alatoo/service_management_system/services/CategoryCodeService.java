package kg.alatoo.service_management_system.services;

import kg.alatoo.service_management_system.entities.CategoryClick;
import kg.alatoo.service_management_system.repositories.CategoryClickRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CategoryCodeService {

    private final CategoryClickRepository repository;

    private final String[] prefixes = {"A", "B", "C", "D", "E"};

    public CategoryCodeService(CategoryClickRepository repository) {
        this.repository = repository;
    }

    public String registerClickAndGetCode(Long studentId, int categoryIndex) {
        if (studentId == null) {
            throw new IllegalArgumentException("studentId is null");
        }
        if (categoryIndex < 1 || categoryIndex > prefixes.length) {
            throw new IllegalArgumentException("Unknown category index: " + categoryIndex);
        }

        LocalDate today = LocalDate.now();
        LocalDateTime from = today.atStartOfDay();
        LocalDateTime to   = today.plusDays(1).atStartOfDay();

        long countToday = repository.countByCategoryIndexAndCreatedAtBetween(
                categoryIndex,
                from,
                to
        );

        long nextNumber = countToday + 1;
        String code = prefixes[categoryIndex - 1] + nextNumber;

        CategoryClick click = new CategoryClick();
        click.setStudentId(studentId);
        click.setCategoryIndex(categoryIndex);
        click.setCreatedAt(LocalDateTime.now());
        repository.save(click);

        return code;
    }
}
