package ru.alishev.springcourse.FirstRestApp.controllers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.models.Person;
import ru.alishev.springcourse.FirstRestApp.services.PeopleService;
import ru.alishev.springcourse.FirstRestApp.util.PersonErrorResponse;
import ru.alishev.springcourse.FirstRestApp.util.PersonNotCreatedException;
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

    @PostMapping //POST-запрос на адрес /people
    public ResponseEntity<HttpStatus> create( //можем возвращать любой объект. Можем вернуть созданного человека
          @RequestBody @Valid Person person, BindingResult bindingResult) { //JSON будет сконвертирован в объект + HB проверит на валидность этот объект
        if(bindingResult.hasErrors()){
            //Соберём все полученные ошибки в одну большую строку и отправим клиенту, чтобы он понял, что сделал не так
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMsg.append(error.getField()) //укажем, на каком поле была совершена ошибка
                .append(" - ").append(error.getDefaultMessage()) //какая ошибка была
                .append(";");                       //чтобы все ошибки не склеились в одну большую строку
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.save(person);
        return ResponseEntity.ok(HttpStatus.OK); //стандартный HTTP ответ при успешной операции. Пустое тело + статус 200
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){
        PersonErrorResponse response = new PersonErrorResponse( //создаём объект с ошибкой
                e.getMessage(), //кладём то, что есть в самом исключении
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);// response-тело HTTP ответа(JSON), 404 status (в заголовке)
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){
        PersonErrorResponse response = new PersonErrorResponse( //создаём объект с ошибкой
                "Person with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//response-тело HTTP ответа(JSON), 400 status (в заголовке)
    }


}
