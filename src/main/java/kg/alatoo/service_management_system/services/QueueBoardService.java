package kg.alatoo.service_management_system.services;

import kg.alatoo.service_management_system.entities.CategoryClick;
import kg.alatoo.service_management_system.entities.CategoryStatus;
import kg.alatoo.service_management_system.repositories.CategoryClickRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QueueBoardService {

    private final CategoryClickRepository repository;

    // Максимум 10 ожидающих слева
    private static final int MAX_WAITING = 10;
    // Максимум 4 обслуживаются справа
    private static final int MAX_IN_PROGRESS = 4;

    public QueueBoardService(CategoryClickRepository repository) {
        this.repository = repository;
    }

    public List<CategoryClick> getTodayWaiting() {
        return getTodayByStatus(CategoryStatus.WAITING, MAX_WAITING);
    }

    public List<CategoryClick> getTodayInProgress() {
        return getTodayByStatus(CategoryStatus.IN_PROGRESS, MAX_IN_PROGRESS);
    }

    private List<CategoryClick> getTodayByStatus(CategoryStatus status, int limit) {
        LocalDate today = LocalDate.now();
        LocalDateTime from = today.atStartOfDay();
        LocalDateTime to   = today.plusDays(1).atStartOfDay();

        List<CategoryClick> all = repository
                .findByStatusAndCreatedAtBetweenOrderByCreatedAtAsc(status, from, to);

        if (all.size() > limit) {
            return all.subList(0, limit);
        }
        return all;
    }
}
