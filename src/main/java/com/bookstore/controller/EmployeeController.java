package com.bookstore.controller;


import com.bookstore.dto.request.EmployeeRequest;
import com.bookstore.dto.response.EmployeeResponse;
import com.bookstore.service.IEmployeeService;
import com.bookstore.validation.TcKimlikNo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@CrossOrigin
@RequestScope
@RequestMapping("/employee")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final IEmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Validated EmployeeRequest employeeRequest){
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
    }
    @PutMapping
    public ResponseEntity<EmployeeResponse> updateStudent(@RequestBody @Validated EmployeeRequest employeeRequest){
        return ResponseEntity.ok(employeeService.updateEmployee(employeeRequest));
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable  @Validated @TcKimlikNo String employeeId){
        employeeService.deleteEmployeeById(employeeId);
    }

    @GetMapping("/{identityNo}")
    public ResponseEntity<EmployeeResponse> findEmployeeByIdentityNo(@PathVariable String identityNo){
        return ResponseEntity.ok(employeeService.findEmployeeByIdentityNo(identityNo));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findAllEmployees(){
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

}
