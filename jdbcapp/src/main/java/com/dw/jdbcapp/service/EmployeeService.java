package com.dw.jdbcapp.service;

import com.dw.jdbcapp.dto.EmployeeDepartmentDTO;
import com.dw.jdbcapp.exception.InvalidRequestException;
import com.dw.jdbcapp.exception.ResourceNotFoundException;
import com.dw.jdbcapp.model.Employee;
import com.dw.jdbcapp.repository.iface.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    @Autowired
    @Qualifier("employeeTemplateRepository")
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.getEmployeeById(id);
    }

    public List<Map<String,Object>> getEmployeesWithDepartName() {
        return employeeRepository.getEmployeesWithDepartName();
    }

    public List<EmployeeDepartmentDTO> getEmployeesWithDepartName2() {
        List<EmployeeDepartmentDTO> employeeDepartmentDTOList =
                new ArrayList<>();

        List<Map<String,Object>> mapList =
                employeeRepository.getEmployeesWithDepartName();

        for(Map<String,Object> data : mapList) {
            EmployeeDepartmentDTO temp = new EmployeeDepartmentDTO(
                    LocalDate.parse((String)data.get("입사일")),
                    (String)data.get("부서명"),
                    (String)data.get("이름")
            );
            employeeDepartmentDTOList.add(temp);
        }
        return employeeDepartmentDTOList;
    }

    public List<Employee> getEmployeesWithDepartmentAndPosition(
            String departmentNumber, String position
    ) {
        List<Employee> employees = employeeRepository.
                getEmployeesWithDepartmentAndPosition(departmentNumber,position);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("존재하지 않는 사원 : "
                    + departmentNumber + ", " + position);
        }else {
            return employeeRepository.getEmployeesWithDepartmentAndPosition(
                    departmentNumber, position);
        }
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.saveEmployee(employee);
    }

    // 12.20 - Q3. 입사일을 매개변수로 입사한 사원들을 조회하는 API
    public List<Employee> getEmployeesByHireDate(String hireDate) {
        if (hireDate==null|| hireDate.isEmpty()) {
            throw new InvalidRequestException("입력된 값이 없습니다.");
            // 12.20 - Q3 - 3. 입사일을 매개변수로 입사한 사원들을 조회하는 API
            // 예외처리(0도 아니고, LocalDate 형태도 아님 => 예외
        }
        try {
            if (hireDate.equals("0")) {
                // 12.20 - Q3 - 2. 입사일을 매개변수로 입사한 사원들을 조회하는 API
                // hireDate를 0으로 입력 시 가장 최근 입사한 사원들의 정보를 조회
                return employeeRepository.getEmployeesByHireDate2();
            } else {
                LocalDate LDHireDate = LocalDate.parse(hireDate);

                // 12.20 - Q3 - 1. 입사일을 매개변수로 입사한 사원들을 조회하는 API
                return employeeRepository.getEmployeesByHireDate(LDHireDate.toString());
            }
        } catch (DateTimeException e) {
            // 예외처리(0도 아니고, LocalDate 형태도 아님 => 예외
            throw new InvalidRequestException("올바르지 않은 입사일을 입력하였습니다 : " + hireDate);
        }
    }
}