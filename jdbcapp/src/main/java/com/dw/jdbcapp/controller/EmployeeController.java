package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.dto.EmployeeDepartmentDTO;
import com.dw.jdbcapp.model.Employee;
import com.dw.jdbcapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/find-all-employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(
                employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/employees/department")
    public ResponseEntity<List<Map<String, Object>>> getEmployeesWithDepartName() {
        return new ResponseEntity<>(
                employeeService.getEmployeesWithDepartName(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/employees/department2")
    public ResponseEntity<List<EmployeeDepartmentDTO>> getEmployeesWithDepartName2() {
        return new ResponseEntity<>(
                employeeService.getEmployeesWithDepartName2(), HttpStatus.ACCEPTED);
    }

    // Query Parameters (쿼리 문자열) http://localhost:8080/employee?id=E01
    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam String id) {
        return new ResponseEntity<>(
                employeeService.getEmployeeById(id), HttpStatus.ACCEPTED);
    }

    // Path Parameters (경로 매개변수) http://localhost:8080/employee/E01
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById_2(@PathVariable String id) {
        return new ResponseEntity<>(
                employeeService.getEmployeeById(id), HttpStatus.ACCEPTED);
    }

    // 2024.12.13 - Q3. 부서번호와 직위를 기준으로 해당 부서에 근무하는 특정 직위의 사원 정보를 조회하는 API
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployeesWithDepartmentAndPosition2
            (@RequestParam String departmentNumber, @RequestParam String position) {
        return new ResponseEntity<>(
                employeeService.getEmployeesWithDepartmentAndPosition(departmentNumber, position),
                HttpStatus.OK);
    }

    // 2024.12.13 - Q3. 부서번호와 직위를 기준으로 해당 부서에 근무하는 특정 직위의 사원 정보를 조회하는 API
    @GetMapping("/employees/{departmentNumber}/{position}")
    public ResponseEntity<List<Employee>> getEmployeesWithDepartmentAndPosition
            (@PathVariable String departmentNumber, @PathVariable String position) {
        return new ResponseEntity<>(
                employeeService.getEmployeesWithDepartmentAndPosition(departmentNumber, position),
                HttpStatus.OK);
    }

    // 2024.12.16 - Q3. 사원테이블에 사원 1명을 새로 추가하는 API
    @PostMapping("/post/employee")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(
                employeeService.saveEmployee(employee), HttpStatus.ACCEPTED);
    }

    @GetMapping("/employees/hireDate/{hireDate}")
    public ResponseEntity<List<Employee>> getEmployeesByHireDate(@PathVariable String hireDate) {
        return new ResponseEntity<>(
                employeeService.getEmployeesByHireDate(hireDate), HttpStatus.ACCEPTED);
    }
}
