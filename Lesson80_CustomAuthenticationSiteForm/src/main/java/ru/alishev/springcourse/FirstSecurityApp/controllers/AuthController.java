package ru.alishev.springcourse.FirstSecurityApp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping
    public String registrationPage(
            @ModelAttribute("person") Person person //Передать человека для thymeleaf формы
            ){
        return "auth/registration";
    }

}
