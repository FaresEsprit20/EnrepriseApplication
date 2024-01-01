package com.stage.teamb.controllers;

import com.stage.teamb.dtos.auth.AuthenticationResponse;
import com.stage.teamb.dtos.auth.RegisterRequest;
import com.stage.teamb.dtos.employee.EmployeeDTO;
import com.stage.teamb.services.auth.AuthenticationService;
import com.stage.teamb.services.employee.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AuthenticationService authenticationService) {
        this.employeeService = employeeService;
        this.authenticationService = authenticationService;
    }

    @PreAuthorize("hasRole('RESPONSIBLE')")
    @PostMapping("/register/employee")
    public ResponseEntity<AuthenticationResponse> registerEmployee(
            @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authenticationService.registerEmployee(request, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.findEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.saveEmployee(employeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/department/find/all/{id}")
//    public ResponseEntity<List<EmployeeDTO>> getEmployeesFromDepartment(@PathVariable Long id) {
//        List<EmployeeDTO> employeeDTOList = employeeService.findEmployeesByDepartmentId(id);
//        return ResponseEntity.ok(employeeDTOList);
//    }
//
//    @PostMapping("/{employeeId}/address/{addressId}")
//    public ResponseEntity<AddressDTO> associateEmployeeWithAddress(
//            @PathVariable Long employeeId, @PathVariable Long addressId) {
//        AddressDTO address = employeeService.associateEmployeeWithAddress(addressId, employeeId);
//        return ResponseEntity.ok(address);
//    }
//
//    @DeleteMapping("/{addressId}/disassociate")
//    public ResponseEntity<AddressDTO> disassociateEmployeeFromAddress(@PathVariable Long addressId) {
//        AddressDTO address = employeeService.disassociateEmployeeFromAddress(addressId);
//        return ResponseEntity.ok(address);
//    }
//
//    @GetMapping("/{departmentId}/employees")
//    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable Long departmentId) {
//        List<EmployeeDTO> employees = employeeService.findEmployeesByDepartmentId(departmentId);
//        return ResponseEntity.ok(employees);
//    }
//
//    @PostMapping("/{employeeId}/department/{departmentId}")
//    public ResponseEntity<DepartmentDTO> assignDepartmentToEmployee(
//            @PathVariable Long employeeId, @PathVariable Long departmentId) {
//        DepartmentDTO department = employeeService.assignDepartmentToEmployee(employeeId, departmentId);
//        return ResponseEntity.ok(department);
//    }
//
//    @DeleteMapping("/{employeeId}/unassign")
//    public ResponseEntity<DepartmentDTO> unassignDepartmentFromEmployee(@PathVariable Long employeeId) {
//        DepartmentDTO department = employeeService.unassignDepartmentFromEmployee(employeeId);
//        return ResponseEntity.ok(department);
//    }



}
