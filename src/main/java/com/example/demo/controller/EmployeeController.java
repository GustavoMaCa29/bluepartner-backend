package com.example.demo.controller;

import com.example.demo.repository.dto.EmployeeCreateDTO;
import com.example.demo.repository.dto.EmployeeDetailsDTO;
import com.example.demo.repository.dto.EmployeeSummaryDTO;
import com.example.demo.repository.entity.Employee;
import com.example.demo.service.interfaces.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Listado de empleados", description = "Obtiene el listado de empleados")
    @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida exitosamente")
    @GetMapping("/list")
    public Page<EmployeeSummaryDTO> getEmployees(Pageable page) {
        return employeeService.getAllEmployees(page);
    }

    @Operation(summary = "Búsqueda de empleado por Id", description = "Busca un empleado por Id")
    @ApiResponse(responseCode = "200", description = "Empleado encontrado")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDetailsDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDetailsDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Crear empleado", description = "Crea un nuevo empleado")
    @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeCreateDTO employee) {
        Employee newEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = "Editar empleado", description = "Edita un empleado por Id")
    @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@Valid @RequestBody EmployeeDetailsDTO employee, @PathVariable("id") Long id) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado por Id")
    @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
