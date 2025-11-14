package kg.alatoo.service_management_system.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String faculty;

    @Column(name = "department")
    private String department;

    public Student() { }

    public Student(String firstname, String lastname, String faculty, String department) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.faculty = faculty;
        this.department = department;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstname; }
    public void setFirstName(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
