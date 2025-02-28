package com.example.demo.service.interfaces;

import com.example.demo.repository.dto.EmployeeCreateDTO;
import com.example.demo.repository.dto.EmployeeDetailsDTO;
import com.example.demo.repository.dto.EmployeeSummaryDTO;
import com.example.demo.repository.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<EmployeeSummaryDTO> getAllEmployees(Pageable page);
    EmployeeDetailsDTO getEmployeeById(Long documentId);
    Employee createEmployee(EmployeeCreateDTO employee);
    Employee updateEmployee(Long documentId, EmployeeDetailsDTO  employee);
    void deleteEmployee(Long id);
}
