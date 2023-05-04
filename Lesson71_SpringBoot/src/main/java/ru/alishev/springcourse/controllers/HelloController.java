package ru.alishev.springcourse.controllers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  //import org.springframework.stereotype.Controller;
public class HelloController {
    @Value("${hello}")  //подтягивается из application.properties
    private String hello;

    @GetMapping("/hello")
    public String hello(){
        System.out.println(this.hello);
        return "hello";
    }
}
