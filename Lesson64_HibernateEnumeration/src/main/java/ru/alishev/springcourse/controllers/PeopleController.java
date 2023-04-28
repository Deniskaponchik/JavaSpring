package ru.alishev.springcourse.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.services.ItemsService;
import ru.alishev.springcourse.services.PeopleService;
import javax.validation.Valid;

//в Контроллерах мы напрямую не обращаемся к Репозиториям. Работаем через Сервис
@Controller
@RequestMapping("/people")
public class PeopleController {

    /* Класс PersonDAO можно удалить, т.к. всё будет делаться в Сервисе
    private final PersonDAO personDAO;  //осталось от уроков до JPA
    @Autowired
    public PeopleController(PersonDAO personDAO, PeopleService peopleService) { this.peopleService = peopleService;   }
    */
    private final PeopleService peopleService;
    private final ItemsService itemService;

    //public PeopleController() {    }
    @Autowired
    public PeopleController(PeopleService peopleService, ItemsService itemService) {
        this.peopleService = peopleService;
        this.itemService = itemService;
    }


    @GetMapping()  //попадаем в этот метод по запросу GET /people
    public String index(Model model) {    //показывает всех людей
        //List<Person> people = personDAO.index();
        //model.addAttribute("people", personDAO.index()); //получим всех людей из DAO (list people), добавим в модель
        model.addAttribute("people", peopleService.findAll());

        itemService.findByItemName("Airpods");  //нам эти методы нужны, чтобы дебагер до них дошёл
        itemService.findByOwner(peopleService.findAll().get(0));
        peopleService.test();

        return "people/index";  //передадим на отображение в Представление.
        //вернём ту страницу/тот шаблон, который будет отображать список из людей
    }
    @GetMapping("/{id}") //можно сюда поместить любое число и оно будет принято, как аргумент метод
    public String show(@PathVariable("id") int id, Model model){    //показывает конкретного человека
        //получим 1 человека (объект Person) по его id из DAO
        //Person person = personDAO.show(id);
        //model.addAttribute("person", personDAO.show(id));
        model.addAttribute("person", peopleService.findOne(id));
        // и передадим на отображение в представление
        return "people/show";
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


    @PostMapping()     //попадаем по запросу POST /people
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
