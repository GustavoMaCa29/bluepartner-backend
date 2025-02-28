package com.example.demo.service;

import com.example.demo.constants.ErrorMessages;
import com.example.demo.constants.NotFoundException;
import com.example.demo.repository.dto.EmployeeCreateDTO;
import com.example.demo.repository.dto.EmployeeDetailsDTO;
import com.example.demo.repository.dto.EmployeeSummaryDTO;
import com.example.demo.repository.entity.Employee;
import com.example.demo.repository.interfaces.EmployeeRepository;
import com.example.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeCreateDTO employeeCreateDTO;
    private EmployeeDetailsDTO employeeDetailsDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setDocumentId(1L);
        employee.setFirstName("José");
        employee.setLastName("Benitez");
        employee.setAge(30);
        employee.setBirthday(new Date());
        employee.setSalary(50000.0);
        employee.setIsDeleted(false);

        employeeCreateDTO = new EmployeeCreateDTO("José", "Benitez", 30, new Date(), 50000.0);
        employeeDetailsDTO = new EmployeeDetailsDTO(1L, "José", "Benitez", 30, new Date(), 50000.0);
    }

    @Test
    void getAllEmployees_ShouldReturnPageOfEmployees() {
        Page<EmployeeSummaryDTO> page = new PageImpl<>(Arrays.asList());
        when(employeeRepository.findAllEmployeeSummaries(any(Pageable.class))).thenReturn(page);

        Page<EmployeeSummaryDTO> result = employeeService.getAllEmployees(Mockito.mock(Pageable.class));

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeDetails() {
        when(employeeRepository.findEmployeeById(1L)).thenReturn(Optional.of(employeeDetailsDTO));

        EmployeeDetailsDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("José", result.getFirstName());
        assertEquals("Benitez", result.getLastName());
    }

    @Test
    void getEmployeeById_ShouldThrowNotFoundException() {
        when(employeeRepository.findEmployeeById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> employeeService.getEmployeeById(1L));

        assertEquals(ErrorMessages.EMPLOYEE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.createEmployee(employeeCreateDTO);

        assertNotNull(result);
        assertEquals("José", result.getFirstName());
        assertEquals("Benitez", result.getLastName());
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.updateEmployee(1L, employeeDetailsDTO);

        assertNotNull(result);
        assertEquals("José", result.getFirstName());
        assertEquals("Benitez", result.getLastName());
    }

    @Test
    void updateEmployee_ShouldThrowNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> employeeService.updateEmployee(1L, employeeDetailsDTO));

        assertEquals(ErrorMessages.EMPLOYEE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void deleteEmployee_ShouldMarkAsDeleted() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        assertTrue(employee.getIsDeleted());
        assertNotNull(employee.getDeletionDate());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void deleteEmployee_ShouldThrowNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> employeeService.deleteEmployee(1L));

        assertEquals(ErrorMessages.EMPLOYEE_NOT_FOUND, exception.getMessage());
    }
}
