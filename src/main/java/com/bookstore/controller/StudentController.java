package com.bookstore.controller;


import com.bookstore.dto.request.StudentRequest;
import com.bookstore.dto.response.StudentResponse;
import com.bookstore.service.IStudentService;
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
@RequestMapping("/student")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final IStudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody @Validated StudentRequest student){
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PutMapping
    public ResponseEntity<StudentResponse> updateStudent(@RequestBody @Validated StudentRequest studentRequest){
        return ResponseEntity.ok(studentService.updateStudent(studentRequest));
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable @TcKimlikNo @Validated String studentId){
         studentService.deleteStudentById(studentId);
    }

    @GetMapping("/{identityNo}")
    public ResponseEntity<StudentResponse> findStudentByIdentityNo(@PathVariable String identityNo){
        return ResponseEntity.ok(studentService.findStudentByIdentityNo(identityNo));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> findAllStudents(){
        return ResponseEntity.ok(studentService.findAllStudents());
    }


}
