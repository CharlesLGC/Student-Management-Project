package com.charles.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.charles.student.repository.StudentRepository;
import com.charles.student.entity.Student;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // get student by id
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // create new student
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // update student
    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setPhone(updatedStudent.getPhone());
                    student.setAddress(updatedStudent.getAddress());
                    student.setBirthDate(updatedStudent.getBirthDate());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // delete student
    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Student not found");
        }
        return;
    }

    // search students by name or id
    public List<Student> searchStudents(String name) {
        return studentRepository.findByName(name);
    }
}
