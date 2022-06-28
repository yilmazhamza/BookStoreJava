package com.bookstore.service.impl;

import com.bookstore.domain.user.Employee;
import com.bookstore.dto.request.EmployeeRequest;
import com.bookstore.dto.response.EmployeeResponse;
import com.bookstore.exception.UserExistException;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.repository.EmployeeRepository;
import com.bookstore.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private static final String debugId = "7f9d3792-7153-43f3-b277-e87312aa3b1e";

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        if(!isExistEmployee(employeeRequest.getIdentityNo())){
            return modelMapper.map(
                    employeeRepository.save
                            (modelMapper.map(employeeRequest, Employee.class)), EmployeeResponse.class);
        }
        throw new UserExistException
                ("This user already is saving database.. : " , employeeRequest.getIdentityNo(), debugId);
    }

    @Override
    public EmployeeResponse updateEmployee(EmployeeRequest employeeRequest) {
        if(isExistEmployee(employeeRequest.getIdentityNo())){
            var existUser = employeeRepository.getById(employeeRequest.getIdentityNo());
            existUser.setEmployeeType(employeeRequest.getEmployeeType());
            existUser.setPassword(employeeRequest.getPassword());
            existUser.setPhone(employeeRequest.getPhone());
            existUser.setMail(employeeRequest.getMail());
            employeeRepository.save(existUser);
            return modelMapper.map(existUser, EmployeeResponse.class);
        }
        throw new UserNotFoundException
                ("This user  is not in database ... : " , employeeRequest.getIdentityNo(), debugId);
    }

    @Override
    public void deleteEmployeeById(String employeeId) {
        if(isExistEmployee(employeeId)){
            employeeRepository.deleteById(employeeId);
        }
        else{
            throw new UserNotFoundException
                    ("This user  is not in database ... : " , employeeId, debugId);
        }
    }

    @Override
    public EmployeeResponse findEmployeeByIdentityNo(String identityNo) {
        if(isExistEmployee(identityNo)){
            return modelMapper.map(employeeRepository.findById(identityNo), EmployeeResponse.class);
        }
        else throw new UserNotFoundException("Can't find in database this employee -> ", identityNo, debugId);

    }

    @Override
    public List<EmployeeResponse> findAllEmployees() {
        var result = employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .toList();
        return result;
    }

    public boolean isExistEmployee(String  identity){
        return employeeRepository.existsById(identity);
    }

}
