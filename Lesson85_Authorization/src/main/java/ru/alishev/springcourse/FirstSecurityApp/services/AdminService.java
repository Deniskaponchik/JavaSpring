package ru.alishev.springcourse.FirstSecurityApp.services;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @PreAuthorize("hasRole('ROLE_ADMIN') or ('ROLE_SOME_OTHER')")  //Может выполняться только админом. or, and
    public void doAdminStuff(){
        //Доступ к странице как бы открыт для всез USER, но при заходе всё равно получим Whitelabel Error page благодаря этой аннотации
        System.out.println("Only admin here");
    }
}
