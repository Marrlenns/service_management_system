package kg.alatoo.service_management_system.services;

import kg.alatoo.service_management_system.entities.CategoryClick;
import kg.alatoo.service_management_system.repositories.CategoryClickRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CategoryCodeService {

    private final CategoryClickRepository repository;

    // Префиксы для категорий 1..5
    private final String[] prefixes = {"A", "B", "C", "D", "E"};

    public CategoryCodeService(CategoryClickRepository repository) {
        this.repository = repository;
    }

    public String registerClickAndGetCode(Long userId, String role, int categoryIndex) {
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
        click.setUserId(userId);
        click.setRole(role);
        click.setCategoryIndex(categoryIndex);
        click.setCode(code);
        click.setCreatedAt(LocalDateTime.now());

        repository.save(click);

        return code;
    }
}
