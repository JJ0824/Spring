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
        List<Employee> employees = employeeRepository.getEmployeesWithDepartmentAndPosition(departmentNumber,position);
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("존재하지 않는 사원 : "
                    + departmentNumber + ", " + position);
        }else {
            return employeeRepository.getEmployeesWithDepartmentAndPosition(
                    departmentNumber, position);
        }
//        if (departmentNumber.toCharArray()[0]!='E'||!position.equals("사원")||!position.equals("대표이사")||
//                !position.equals("대리")||!position.equals("전산팀장")||!position.equals("수습사원")||
//                !position.equals("과장")||!position.equals("부장")||!position.equals("CEO")||
//                !position.equals("사장")||!position.equals("회장")) {
//            throw new ResourceNotFoundException("존재하지 않는 사원 : "
//                    + departmentNumber + ", " + position);
//        }else {
//            return employeeRepository.getEmployeesWithDepartmentAndPosition(
//                    departmentNumber, position);
//        }
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.saveEmployee(employee);
    }

    public List<Employee> getEmployeesByHireDate(String hireDate) {
        if (hireDate.equals("0")) {
            return employeeRepository.getEmployeesByHireDate2();
        }else {
            try {
                LocalDate LDHireDate = LocalDate.parse(hireDate);

                return employeeRepository.getEmployeesByHireDate(LDHireDate.toString());
            } catch (DateTimeException e) {
                throw new InvalidRequestException("올바르지 않은 입사일을 입력하였습니다 : " + hireDate);
            }
        }
    }
}