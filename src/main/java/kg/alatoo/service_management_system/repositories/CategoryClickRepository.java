package kg.alatoo.service_management_system.repositories;

import kg.alatoo.service_management_system.entities.CategoryClick;
import kg.alatoo.service_management_system.entities.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CategoryClickRepository extends JpaRepository<CategoryClick, Long> {

    long countByCategoryIndexAndCreatedAtBetween(
            Integer categoryIndex,
            LocalDateTime from,
            LocalDateTime to
    );

    // --- НОВОЕ: для табло ---

    /**
     * Все талоны с заданным статусом за интервал времени,
     * отсортированные по времени создания (старые сначала).
     */
    List<CategoryClick> findByStatusAndCreatedAtBetweenOrderByCreatedAtAsc(
            CategoryStatus status,
            LocalDateTime from,
            LocalDateTime to
    );
}
