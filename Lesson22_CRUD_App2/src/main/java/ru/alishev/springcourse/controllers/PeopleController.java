package ru.alishev.springcourse.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()  //попадаем в этот метод по запросу GET /people
    public String index(Model model) {    //показывает всех людей
        //получим всех людей из DAO (list people), добавим в модель
        //List<Person> people = personDAO.index();
        model.addAttribute("people", personDAO.index());
        //и передадим на отображение в Представление
        //вернём ту страницу/тот шаблон, который будет отображать список из людей
        return "people/index";  //возвращаем представление
    }
    @GetMapping("/{id}") //можно сюда поместить любое число и оно будет принято, как аргумент метод
    public String show(@PathVariable("id") int id, Model model){    //показывает конкретного человека
        //получим 1 человека (объект Person) по его id из DAO
        //Person person = personDAO.show(id);
        model.addAttribute("person", personDAO.show(id));
        // и передадим на отображение в представление
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
    //public String newPerson(Model model){
        //model.addAttribute("person", new Person());
        return "people/new";  //возвращаем название Thymeleaf шаблона
    }


    @PostMapping()  //попадаем в этот метод по запросу POST /people
    //@ModelAttribute = Создание нового человека+Добавление значений сеттерами+Добавление созданного объекта в модель
    //Если форма на метод придёт пустая, будет создан новый объект с полями 0, null
    public String create(@ModelAttribute("person") Person person){
        personDAO.save(person);  //Добавление человека в БД. ModelAttribute этим не занимается
        return "redirect:/people"; //
    }
}
