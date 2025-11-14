package kg.alatoo.service_management_system.services;

import kg.alatoo.service_management_system.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Optional<String> getNameById(Long id) {
        return repository.findNameById(id);
    }
}
