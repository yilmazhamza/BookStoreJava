package com.bookstore.service;

import com.bookstore.dto.request.EmployeeRequest;
import com.bookstore.dto.response.EmployeeResponse;

import java.util.List;

public interface IEmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse updateEmployee(EmployeeRequest employeeRequest);
    void deleteEmployeeById(String employeeId);
    EmployeeResponse findEmployeeByIdentityNo(String identityNo);
    List<EmployeeResponse> findAllEmployees();
}
