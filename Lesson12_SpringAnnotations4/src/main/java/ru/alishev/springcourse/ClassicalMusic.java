package ru.alishev.springcourse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.*;
@Component
public class ClassicalMusic implements Music{
    @PostConstruct
    public void doMyInit(){
        System.out.println("Doing my initialization");
    }
    @PreDestroy
    public void doMyDestroy() {
        System.out.println("Doing my destroy");
    }
    @Override
    public String getSong() {
        return "Hungarian Rhapsody";
    }
}
