package com.bookstore.service.impl;

import com.bookstore.domain.user.Student;
import com.bookstore.dto.request.StudentRequest;
import com.bookstore.dto.response.StudentResponse;
import com.bookstore.exception.UserExistException;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.repository.StudentRepository;
import com.bookstore.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    private static final String debugId = "4ad17f40-a8e8-45fc-a8c9-5f5b4b1d6584";


    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        if(!isExistStudent(studentRequest.getIdentityNo())){
            log.info("User saved on system... : " + studentRequest.getStudentNo());
            return modelMapper.map(
                    studentRepository.save
                            (modelMapper.map(studentRequest,Student.class)),StudentResponse.class);
        }
         throw new UserExistException
                ("This user already is saving database.. : " , studentRequest.getIdentityNo(), debugId);
    }

    @Override
    public void deleteStudentById(String studentId) {
        if(isExistStudent(studentId)){
            log.info("User deleted on system... : " + studentId);
            studentRepository.deleteById(studentId);
        }
        else{
            throw new UserNotFoundException
                    ("This user  is not in database ... : " , studentId, debugId);
        }

    }

    @Override
    public StudentResponse updateStudent(StudentRequest studentRequest) {
        if(isExistStudent(studentRequest.getIdentityNo())){
            var existUser = studentRepository.getById(studentRequest.getIdentityNo());
            existUser.setStudentStatus(studentRequest.getStudentStatus());
            existUser.setPassword(studentRequest.getPassword());
            existUser.setHasBook(studentRequest.getHasBook());
            existUser.setPhone(studentRequest.getPhone());
            existUser.setMail(studentRequest.getMail());
            studentRepository.save(existUser);
            return modelMapper.map(existUser,StudentResponse.class);
        }
        throw new UserNotFoundException
                ("This user  is not in database ... : " , studentRequest.getIdentityNo(), debugId);
    }


    @Override
    public StudentResponse findStudentByIdentityNo(String identityNo) {
        if(isExistStudent(identityNo)){
            return modelMapper.map(studentRepository.findById(identityNo), StudentResponse.class);
        }
        else throw new UserNotFoundException("Can't find in database this student -> ", identityNo, debugId);

    }

    @Override
    public List<StudentResponse> findAllStudents() {
        var result = studentRepository.findAll().stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
                .toList();
        return result;
    }

    public boolean isExistStudent(String  identity){
        return studentRepository.existsById(identity);
    }



}
