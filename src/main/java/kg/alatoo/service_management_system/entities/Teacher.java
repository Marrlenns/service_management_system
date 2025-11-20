package kg.alatoo.service_management_system.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String faculty;

    private String department;

    @Column(name = "email", unique = true)
    private String email;

    public Teacher() {
    }

    public Teacher(String firstname, String lastname,
                   String faculty, String department, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.faculty = faculty;
        this.department = department;
        this.email = email;
    }

    public Long getId() { return id; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
