package ru.alishev.springcourse.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.util.PersonValidator;
import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    @GetMapping()
    public String index(Model model) {    //показывает всех людей
        //получим всех людей из DAO (list people), добавим в модель
        model.addAttribute("people", personDAO.index()); //personDAO.index() = List<Person>
        return "people/index";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";  //возвращаем название Thymeleaf шаблона
    }
    //public String newPerson(Model model){
        //model.addAttribute("person", new Person());
        //return "people/new";
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){ //СОЗДАЁМ НОВУЮ модель
        //получим 1 человека (объект Person) по его id из DAO
        model.addAttribute("person", personDAO.show(id));  //Person person = personDAO.show(id);
        model.addAttribute("personBooks", personDAO.showPersonBooks(id)); //List<Book>
        return "people/show";   // и передадим на отображение в представление
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }




    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
    //@ModelAttribute = Создание нового человека+Добавление значений сеттерами+Добавление созданного объекта в модель
                 BindingResult bindingResult //ДОЛЖЕН идти после валидируемого объекта.
                         // Здесь будут лежать как ошибки с полей HTML страницы, так и ошибки из БД
    ){
        personValidator.validate(person, bindingResult);   //в bindingResult валятся ошибки со всех валидаций
        if(bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);  //Добавление человека в БД. ModelAttribute этим не занимается
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            return "/people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }





    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }
}
