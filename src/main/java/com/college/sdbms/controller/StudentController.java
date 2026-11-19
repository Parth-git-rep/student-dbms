package com.college.sdbms.controller;

import com.college.sdbms.model.Student;
import com.college.sdbms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Home page - redirect to students list
    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }

    // List all students (with optional search)
    @GetMapping("/students")
    public String listStudents(@RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentService.searchStudents(keyword.trim());
        } else {
            students = studentService.getAllStudents();
        }

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    // Show add student form
    @GetMapping("/students/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    // Save new student
    @PostMapping("/students/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "add-student";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // Show edit student form
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit-student";
    }

    // Update student
    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("student") Student student,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "edit-student";
        }
        student.setId(id);
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // Delete student
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
