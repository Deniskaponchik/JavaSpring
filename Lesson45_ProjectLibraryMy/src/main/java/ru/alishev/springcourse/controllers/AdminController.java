package ru.alishev.springcourse.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    private final PersonDAO personDAO;

    @GetMapping()
    public String makeAdminPage(Model model, @ModelAttribute("person") Person person){
        model.addAttribute("people", personDAO.index());
        return"adminPage";
    }

    @PatchMapping("/add")
    public String makeAdmin(
            @ModelAttribute("person") Person person){ //человек из выпадающего списка положится в модель
        //объект id с формы положится в созданный моделью объект класса Person
        System.out.println(person.getId());
        //значения всех полей для человека будут null, так как с формы приходит только id
        return "redirect:/people";
    }

}
