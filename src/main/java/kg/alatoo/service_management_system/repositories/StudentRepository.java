package kg.alatoo.service_management_system.repositories;


import kg.alatoo.service_management_system.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s.firstname from Student s where s.id = :id")
    Optional<String> findNameById(@Param("id") Long id);
}
