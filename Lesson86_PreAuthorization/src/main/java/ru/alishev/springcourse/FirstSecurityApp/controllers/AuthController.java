package ru.alishev.springcourse.FirstSecurityApp.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;
import ru.alishev.springcourse.FirstSecurityApp.services.RegistrationService;
import ru.alishev.springcourse.FirstSecurityApp.util.PersonValidator;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;    }

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(
            @ModelAttribute("person") Person person //Передать человека для thymeleaf формы
            ){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(
            @ModelAttribute("person") @Valid Person person, //есть аннотации над полями класса Person, поэтому они будут валидироваться
            BindingResult bindingResult
            ){
        personValidator.validate(person, bindingResult);//дополнительный валидатор по БД, что человека там нет. Человек не сможет зарегистрироваться с именем, которое уже существует
        if(bindingResult.hasErrors())
            return "/auth/registration"; //если есть ошибки
        registrationService.register(person);
        return "redirect:/auth/login"; //после успешной регистрации отправляем на страницу аутентификации
    }

}
