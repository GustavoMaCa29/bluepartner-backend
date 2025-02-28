package com.example.demo.repository.interfaces;

import com.example.demo.repository.dto.EmployeeDetailsDTO;
import com.example.demo.repository.dto.EmployeeSummaryDTO;
import com.example.demo.repository.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT new com.example.demo.repository.dto.EmployeeSummaryDTO(" +
            "e.documentId, " +
            "e.firstName, " +
            "e.lastName, " +
            "e.age, " +
            "e.birthday, " +
            "e.salary) " +
            "FROM Employee e " +
            "WHERE e.isDeleted = false")
    Page<EmployeeSummaryDTO> findAllEmployeeSummaries(Pageable pageable);

    @Query(value = "SELECT new com.example.demo.repository.dto.EmployeeDetailsDTO(" +
            "e.documentId, " +
            "e.firstName, " +
            "e.lastName, " +
            "e.age, " +
            "e.birthday, " +
            "e.salary) " +
            "FROM Employee e " +
            "WHERE e.documentId =:id " +
            "AND e.isDeleted = false")
    Optional<EmployeeDetailsDTO> findEmployeeById(@Param("id") Long id);
}
