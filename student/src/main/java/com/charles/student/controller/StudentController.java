package com.charles.student.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.charles.student.service.StudentService;
import com.charles.student.entity.Student;
import com.charles.student.controller.StudentController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // get all students
    @GetMapping()
    public List<Student> listStudents() {
        return studentService.getAllStudents();
    }

    // get student by id
    @GetMapping("/{id}")
    public Student listStudentWithId(@PathVariable Long id) {
        return studentService.getStudentById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // create new student
    @PostMapping()
    public String creatingStudent(@Valid @RequestBody Student student) {
        return studentService.createStudent(student).toString();
    }

    // update student
    @PutMapping("/{id}")
    public String updatingStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student).toString();
    }

    // delete student
    @DeleteMapping("/{id}")
    public void deletingStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    // search students by name or id
    @GetMapping("/search")
    public List<Student> searchingStudent(@RequestParam String name) {
        return studentService.searchStudents(name);
    }

    // handle invalid requests
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return "{ \"error\": \"" + errorMessage + "\" }";
    }

    // handle not found requests
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundError(RuntimeException ex) {
        return "{ \"error\": \"" + ex.getMessage() + "\" }";
    }
}
