package cmpt276.assn2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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
        students.sort(Comparator.comparingInt(Student::getSid));
        model.addAttribute("stu", students);
        return "students/showAll";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newstudent, HttpServletResponse response, Model model) {
        String newName = newstudent.get("name");
        double newWeight = Double.parseDouble(newstudent.get("weight"));
        double newHeight = Double.parseDouble(newstudent.get("height"));
        String newHaircolor = newstudent.get("haircolor");
        double newGpa = Double.parseDouble(newstudent.get("gpa"));
        studentRepo.save(new Student(newName, newWeight, newHeight, newHaircolor, newGpa));
        response.setStatus(201);
        return "redirect:/students/view";
    }

    @GetMapping("/")
    public RedirectView process(){
        return new RedirectView("/students/view");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") int id) {
        studentRepo.deleteById(id);
        
    return "redirect:/students/view";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") int id, Model model) {
        Student currentStudent = studentRepo.findById(id);
        model.addAttribute("currStu", currentStudent);
    return "students/editStudent";
    }

    @PostMapping("/students/update")
    public String update(@ModelAttribute("currStu") Student updatedStudent, @RequestParam Map<String, String> currform) {
        Student existingStudent = updatedStudent;
        existingStudent.setName(currform.get("name"));
        existingStudent.setWeight(Double.parseDouble(currform.get("weight")));
        existingStudent.setHeight(Double.parseDouble(currform.get("height")));
        existingStudent.setHaircolor(currform.get("haircolor"));
        existingStudent.setGpa(Double.parseDouble(currform.get("gpa")));
        studentRepo.save(existingStudent);
        return "redirect:/students/view";
    }
    

}
