package com.dw.jdbcapp.repository.iface;

import com.dw.jdbcapp.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(String id);
    List<Map<String,Object>> getEmployeesWithDepartName();
    List<Employee> getEmployeesWithDepartmentAndPosition(String departmentNumber, String position);
    Employee saveEmployee(Employee employee);
    // 12.20 - Q3. 입사일을 매개변수로 입사한 사원들을 조회하는 API
    List<Employee> getEmployeesByHireDate(String hireDate);

    List<Employee> getEmployeesByHireDate2();
}
