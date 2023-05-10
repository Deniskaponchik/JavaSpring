package ru.alishev.springcourse.FirstRestApp.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.models.Person;
import ru.alishev.springcourse.FirstRestApp.services.PeopleService;
import ru.alishev.springcourse.FirstRestApp.util.PersonErrorResponse;
import ru.alishev.springcourse.FirstRestApp.util.PersonNotFoundException;

import java.util.List;


// @Controller
@RestController  //@ResponseBody над каждым методом
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    //@ResponseBody над каждым методом уже прописан
    @GetMapping()
    public List<Person> getPeople() {
        return peopleService.findAll(); // HB конвертирует строки из БД в объекты Java + Jackson конвертирует эти объекты в JSON
    }

    //@ResponseBody над каждым методом уже прописан
    @GetMapping("/{id}")
    public Person getPerson(
            @PathVariable("id") int id){
        return peopleService.findOne(id); // HB конвертирует строки из БД в объекты Java + Jackson конвертирует в JSON
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){
        PersonErrorResponse response = new PersonErrorResponse( //создаём объект с ошибкой
                "Person with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);// response-тело HTTP ответа(JSON), 404 status (в заголовке)
    }
}
