package kg.alatoo.service_management_system.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "category_clicks")
public class CategoryClick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "category_index", nullable = false)
    private Integer categoryIndex; // 1..5

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public CategoryClick() {
    }

    public CategoryClick(Long studentId, Integer categoryIndex, LocalDateTime createdAt) {
        this.studentId = studentId;
        this.categoryIndex = categoryIndex;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(Integer categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
