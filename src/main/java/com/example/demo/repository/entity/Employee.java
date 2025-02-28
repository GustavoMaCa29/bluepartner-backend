package com.example.demo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @Column(name = "document_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @NotNull(message = "El nombre del empleado no puede estar vacío.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "El apellido del empleado no puede estar vacío.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "La edad del empleado no puede estar vacío.")
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull(message = "La fecha de nacimiento del empleado no puede estar vacío.")
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @NotNull(message = "El salario del empleado no puede estar vacío.")
    @Column(name = "salary", nullable = false)
    private Double salary;

    @Column(name = "creation_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    @Column(name = "deletion_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletionDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}

