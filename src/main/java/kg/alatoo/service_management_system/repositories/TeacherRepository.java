package kg.alatoo.service_management_system.repositories;

import kg.alatoo.service_management_system.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmailIgnoreCase(String email);
}
