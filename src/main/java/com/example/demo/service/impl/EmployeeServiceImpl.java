package com.example.demo.service.impl;

import com.example.demo.constants.ErrorMessages;
import com.example.demo.constants.NotFoundException;
import com.example.demo.repository.dto.EmployeeCreateDTO;
import com.example.demo.repository.dto.EmployeeDetailsDTO;
import com.example.demo.repository.dto.EmployeeSummaryDTO;
import com.example.demo.repository.entity.Employee;
import com.example.demo.repository.interfaces.EmployeeRepository;
import com.example.demo.service.interfaces.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Page<EmployeeSummaryDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAllEmployeeSummaries(pageable);
    }

    @Override
    public EmployeeDetailsDTO getEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.EMPLOYEE_NOT_FOUND));
    }

    @Override
    public Employee createEmployee(EmployeeCreateDTO employee) {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employee.getFirstName());
        newEmployee.setLastName(employee.getLastName());
        newEmployee.setAge(employee.getAge());
        newEmployee.setBirthday(employee.getBirthday());
        newEmployee.setSalary(employee.getSalary());
        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDetailsDTO employeeDTO) {
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.EMPLOYEE_NOT_FOUND));

        updateEmployee.setFirstName(employeeDTO.getFirstName());
        updateEmployee.setLastName(employeeDTO.getLastName());
        updateEmployee.setAge(employeeDTO.getAge());
        updateEmployee.setBirthday(employeeDTO.getBirthday());
        updateEmployee.setSalary(employeeDTO.getSalary());

        return employeeRepository.save(updateEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.EMPLOYEE_NOT_FOUND));

        employee.setIsDeleted(true);
        employee.setDeletionDate(new Date());
        employeeRepository.save(employee);
    }
}
