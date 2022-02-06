package gr.hua.dit.ds.reference.letter.service.controller;

import gr.hua.dit.ds.reference.letter.service.entity.Student;
import gr.hua.dit.ds.reference.letter.service.entity.Teacher;
import gr.hua.dit.ds.reference.letter.service.entity.User;
import gr.hua.dit.ds.reference.letter.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userService.registerUser(user, "ROLE_USER");
        return "redirect:/index";
    }

    @GetMapping("/signup/student")
    public String showStudentSignUpForm(Student student) {
        return "add-student";
    }

    @PostMapping("/addstudent")
    public String addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-student";
        }

        User user = student.getUser();
        // TODO: Save properly the student in the Repository
        userService.registerUser(user, "ROLE_STUDENT");
        return "redirect:/index";
    }

    @GetMapping("/signup/teacher")
    public String showTeacherSignUpForm(Teacher teacher) {
        return "add-teacher";
    }

    @PostMapping("/addteacher")
    public String addTeacher(@Valid Teacher teacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-teacher";
        }

        User user = teacher.getUser();
        // TODO: Save properly the student in the Repository
        userService.registerUser(user, "ROLE_TEACHER");
        return "redirect:/index";
    }

    // TODO: Testing templates for student and teacher

}