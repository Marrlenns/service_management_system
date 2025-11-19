package kg.alatoo.service_management_system.repositories;

import kg.alatoo.service_management_system.entities.CategoryClick;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CategoryClickRepository extends JpaRepository<CategoryClick, Long> {

    long countByCategoryIndexAndCreatedAtBetween(
            Integer categoryIndex,
            LocalDateTime from,
            LocalDateTime to
    );
}
