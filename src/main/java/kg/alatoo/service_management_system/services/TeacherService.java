package kg.alatoo.service_management_system.services;

import kg.alatoo.service_management_system.entities.Teacher;
import kg.alatoo.service_management_system.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public Optional<Teacher> findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }
}
