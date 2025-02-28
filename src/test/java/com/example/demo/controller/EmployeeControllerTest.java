package com.example.demo.controller;

import com.example.demo.repository.dto.EmployeeCreateDTO;
import com.example.demo.repository.dto.EmployeeDetailsDTO;
import com.example.demo.repository.dto.EmployeeSummaryDTO;
import com.example.demo.repository.entity.Employee;
import com.example.demo.service.interfaces.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getEmployees_ShouldReturnEmployeeList() {
        Page<EmployeeSummaryDTO> mockPage = new PageImpl<>(Arrays.asList(new EmployeeSummaryDTO(1L, "José", "Pèrez", 25, null, 3500.0)));
        when(employeeService.getAllEmployees(any(PageRequest.class))).thenReturn(mockPage);

        Page<EmployeeSummaryDTO> result = employeeController.getEmployees(PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getEmployeeById_ShouldReturnEmployeeDetails() {
        EmployeeDetailsDTO mockEmployee = new EmployeeDetailsDTO(1L, "José", "Pèrez", 30, null, 50000.0);
        when(employeeService.getEmployeeById(1L)).thenReturn(mockEmployee);

        ResponseEntity<EmployeeDetailsDTO> response = employeeController.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("José", response.getBody().getFirstName());
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        EmployeeCreateDTO newEmployeeDTO = new EmployeeCreateDTO("José", "Pèrez", 30, null, 50000.0);

        Employee mockEmployee = new Employee();
        mockEmployee.setFirstName("José");
        mockEmployee.setLastName("Pèrez");
        mockEmployee.setAge(30);
        mockEmployee.setBirthday(null);
        mockEmployee.setSalary(50000.0);

        when(employeeService.createEmployee(any(EmployeeCreateDTO.class))).thenReturn(mockEmployee);
        ResponseEntity<Employee> response = employeeController.createEmployee(newEmployeeDTO);

        assertNotNull(response.getBody());
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("José", response.getBody().getFirstName());
    }

    @Test
    void updateEmployeeById_ShouldReturnUpdatedEmployee() {
        EmployeeDetailsDTO updatedEmployeeDTO = new EmployeeDetailsDTO(1L, "José", "Pèrez", 30, null, 55000.0);

        Employee mockUpdatedEmployee = new Employee();
        mockUpdatedEmployee.setDocumentId(1L);
        mockUpdatedEmployee.setFirstName("José");
        mockUpdatedEmployee.setLastName("Pèrez");
        mockUpdatedEmployee.setAge(30);
        mockUpdatedEmployee.setBirthday(null);
        mockUpdatedEmployee.setSalary(55000.0);

        when(employeeService.updateEmployee(eq(1L), any(EmployeeDetailsDTO.class))).thenReturn(mockUpdatedEmployee);

        ResponseEntity<Employee> response = employeeController.updateEmployeeById(updatedEmployeeDTO, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Pèrez", response.getBody().getLastName());
    }

    @Test
    void deleteEmployeeById_ShouldCallService() {
        doNothing().when(employeeService).deleteEmployee(1L);

        employeeController.deleteEmployeeById(1L);

        verify(employeeService, times(1)).deleteEmployee(1L);
    }
}
