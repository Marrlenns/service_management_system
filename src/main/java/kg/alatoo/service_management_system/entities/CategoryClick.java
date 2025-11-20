package kg.alatoo.service_management_system.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "category_clicks")
public class CategoryClick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Общий идентификатор пользователя:
     * - для студента: id студента
     * - для преподавателя: id преподавателя
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Роль пользователя: "STUDENT" или "TEACHER"
     */
    @Column(name = "role")
    private String role;

    /**
     * Номер категории (1..5)
     */
    @Column(name = "category_index", nullable = false)
    private Integer categoryIndex;

    /**
     * Сохранённый код, который показывали на талоне:
     * "A1", "Б2", "В10" и т.п.
     */
    @Column(name = "code")
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public CategoryClick() {
    }

    public CategoryClick(Long userId,
                         String role,
                         Integer categoryIndex,
                         String code,
                         LocalDateTime createdAt) {
        this.userId = userId;
        this.role = role;
        this.categoryIndex = categoryIndex;
        this.code = code;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(Integer categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
