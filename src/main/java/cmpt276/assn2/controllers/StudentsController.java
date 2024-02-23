package cmpt276.assn2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cmpt276.assn2.models.Student;
import cmpt276.assn2.models.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StudentsController {
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllStudents(Model model) {
        List<Student> students = studentRepo.findAll();

        model.addAttribute("stu", students);
        return "students/showAll";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newstudent, HttpServletResponse response, Model model) {
        String newName = newstudent.get("name");
        double newHeight = Double.parseDouble(newstudent.get("height"));
        double newWeight = Double.parseDouble(newstudent.get("weight"));
        String newHaircolor = newstudent.get("haircolor");
        double newGpa = Double.parseDouble(newstudent.get("gpa"));
        studentRepo.save(new Student(newName, newWeight, newHeight, newHaircolor, newGpa));
        response.setStatus(201);
        return "students/addedStudent";
    }

}
