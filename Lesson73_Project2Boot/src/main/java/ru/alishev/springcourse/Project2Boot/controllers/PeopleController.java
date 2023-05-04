package ru.alishev.springcourse.Project2Boot.controllers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.services.BooksService;
import ru.alishev.springcourse.Project2Boot.services.PeopleService;


//в Контроллерах мы напрямую не обращаемся к Репозиториям. Работаем через Сервис
@Controller
@RequestMapping("/people")
public class PeopleController {

    //private final PersonDAO personDAO; //будет внедрён с помощью спринга, т.к. помечен @Component
    private final PeopleService peopleService;  //JPA
    private final BooksService booksService;  //в проекте не используется

    //public PeopleController() {    }
    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        //this.personDAO = personDAO;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }


    @GetMapping()
    public String index(Model model) {
        //List<Person> people = personDAO.index();
        //model.addAttribute("people", personDAO.index()); //получим всех людей из DAO (list people), добавим в модель

        model.addAttribute("people", peopleService.findAll());
        //personDAO.testNPlus1();

        //itemService.findByItemName("Airpods");                   //нам эти методы нужны, чтобы дебагер до них дошёл
        //itemService.findByOwner(peopleService.findAll().get(0)); //использовалось в уроке с дебаггером
        //peopleService.test();                                    //использовалось в уроке с дебаггером

        return "people/index";  //передадим на отображение в Представление.
        //вернём ту страницу/тот шаблон, который будет отображать список из людей
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){    //показывает конкретного человека
        //model.addAttribute("person", personDAO.show(id));                   //Person person = personDAO.show(id)
        model.addAttribute("person", peopleService.findOne(id));   //Person person = peopleService.findOne(id)
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "people/show";  // и передадим на отображение в представление
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        //model.addAttribute("person", personDAO.show(id));
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
    //public String newPerson(Model model){
        //model.addAttribute("person", new Person());
        return "people/new";  //возвращаем название Thymeleaf шаблона
    }


    @PostMapping()  //Добавление нового человека с формы new
    public String create(@ModelAttribute("person") @Valid Person person,
                         //@ModelAttribute = Создание нового человека+Добавление значений сеттерами+Добавление созданного объекта в модель
                         BindingResult bindingResult //ДОЛЖЕН идти после валидируемого объекта. Здесь будут лежать ошибки валидации
    ){
        if(bindingResult.hasErrors())
            return "people/new";
        //personDAO.save(person);  //Добавление человека в БД. ModelAttribute этим не занимается
        peopleService.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "/people/edit";

        //personDAO.update(id, person);
        peopleService.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        //personDAO.delete(id);
        peopleService.delete(id);
        return "redirect:/people";
    }
}
