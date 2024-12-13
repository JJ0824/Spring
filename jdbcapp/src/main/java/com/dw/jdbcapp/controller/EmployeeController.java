package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.dto.EmployeeDepartmentDTO;
import com.dw.jdbcapp.model.Employee;
import com.dw.jdbcapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/find-all-employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/department")
    public List<Map<String, Object>> getEmployeesWithDepartName() {
        return employeeService.getEmployeesWithDepartName();
    }

    @GetMapping("/employees/department2")
    public List<EmployeeDepartmentDTO> getEmployeesWithDepartName2() {
        return employeeService.getEmployeesWithDepartName2();
    }

    // Query Parameters (쿼리 문자열) http://localhost:8080/employee?id=E01
    @GetMapping("/employee")
    public Employee getEmployeeById(@RequestParam String id) {
        return employeeService.getEmployeeById(id);
    }

    // Path Parameters (경로 매개변수) http://localhost:8080/employee/E01
    @GetMapping("/employee/{id}")
    public Employee getEmployeeById_2(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/api/employees/{departmentNumber}/{position}")
    public List<Employee> getEmployeeByDepartmentNumberAndPosition(@PathVariable String departmentNumber,@PathVariable String position) {
        return employeeService.getEmployeeByDepartmentNumberAndPosition(departmentNumber, position);
    }
}
