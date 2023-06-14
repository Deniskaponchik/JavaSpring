package ru.alishev.springcourse.FirstRestApp.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController  //Каждый метод будет иметь @ResponseBody по умолчанию
@RequestMapping("/api")
public class FirstRestController {

    //@ResponseBody  //уже заложено в @RestController
    @GetMapping("/sayHello")
    public String sayHello(){
        return "Hello world!"; //Клиент получит ИМЕННО строку, а не шаблон, как раньше. Возвращаем данные, а не представление
    }
}
