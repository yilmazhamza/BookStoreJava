package com.bookstore.service;

import com.bookstore.dto.request.StudentRequest;
import com.bookstore.dto.response.StudentResponse;

import java.util.List;

public interface IStudentService {
    StudentResponse createStudent(StudentRequest student);
    void deleteStudentById(String studentId);
    StudentResponse updateStudent(StudentRequest studentRequest);

    StudentResponse findStudentByIdentityNo(String identityNo);

    List<StudentResponse> findAllStudents();
}
