package com.college.sdbms.service;

import com.college.sdbms.model.Student;
import com.college.sdbms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // Save a new student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Delete student by ID
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // Search students by keyword
    public List<Student> searchStudents(String keyword) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                keyword, keyword);
    }
}
