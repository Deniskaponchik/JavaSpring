package ru.alishev.springcourse.FirstRestApp.controllers;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.SensorDTO;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.services.SensorsService;
import java.util.List;
import java.util.stream.Collectors;
import ru.alishev.springcourse.FirstRestApp.util.*;
import static ru.alishev.springcourse.FirstRestApp.util.ErrorsUtil.returnErrorsToClient;

// @Controller
@RestController  //@ResponseBody над каждым методом
//@RequestMapping("/people")
@RequestMapping("/sensors")
public class SensorsController {
    //private final PeopleService peopleService;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;//Необходим при работе с DTO. Уменьшает кол-во кода
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(//PeopleService peopleService
                             SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        //this.peopleService = peopleService;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }



    /*@ResponseBody над каждым методом уже прописан
    @GetMapping()
    public List<Person> getPeople() { //Обращаемся напрямую к модели, что НЕ ПРАВИЛЬНО
        return peopleService.findAll(); // HB конвертирует строки из БД в объекты Java + Jackson конвертирует эти объекты в JSON
    }
    @GetMapping()  //GET-запрос по адресу /people
    public List<PersonDTO> getPeople() { //Убираем подключение к модели напрямую и включаем подключение через DTO
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    } // HB конвертирует строки из БД в объекты Java + Jackson конвертирует эти объекты в JSON */




    /*@ResponseBody над каждым методом уже прописан
    @GetMapping("/{id}")
    public Person getPerson( @PathVariable("id") int id){ //Обращаемся напрямую к модели, что НЕ ПРАВИЛЬНО
        return peopleService.findOne(id); // HB конвертирует строки из БД в объекты Java + Jackson конвертирует в JSON
    }
    @GetMapping("/{id}")
    public PersonDTO getPerson( @PathVariable("id") int id){
        return convertToPersonDTO(peopleService.findOne(id));
    }*/





    /*
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
    }*/  /*
    @PostMapping //POST-запрос на адрес /people
    public ResponseEntity<HttpStatus> create( //можем возвращать любой объект. Можем вернуть созданного человека
                                              //@RequestBody @Valid Person person,
                                              @RequestBody @Valid PersonDTO personDTO,
                                              BindingResult bindingResult) { //JSON будет сконвертирован в объект + HB проверит на валидность этот объект
        if (bindingResult.hasErrors()) {
            //Соберём все полученные ошибки в одну большую строку и отправим клиенту, чтобы он понял, что сделал не так
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()) //укажем, на каком поле была совершена ошибка
                        .append(" - ").append(error.getDefaultMessage()) //какая ошибка была
                        .append(";");                       //чтобы все ошибки не склеились в одну большую строку
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        //peopleService.save(person);
        peopleService.save(convertToPerson(personDTO));//конвертируем объект, пришедший от клиента в объект модели
        return ResponseEntity.ok(HttpStatus.OK); //стандартный HTTP ответ при успешной операции. Пустое тело + статус 200
    }*/
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        Sensor sensorToAdd = convertToSensor(sensorDTO);

        sensorValidator.validate(sensorToAdd, bindingResult);//Spring Validator. НЕ должно быть сенсоров с одинаковым именем

        if (bindingResult.hasErrors()) {//Ошибки могут прийти как от валидатора, так и от
            returnErrorsToClient(bindingResult);//import static ru.alishev.springcourse.FirstRestApp.util.ErrorsUtil.returnErrorsToClient;
            //если запрос не валидный, то здесь выбросится исключение и код дальше не пойдёт
        }

        sensorsService.register(sensorToAdd);//если запрос валидный, то регистрируем новый сенсор
        return ResponseEntity.ok(HttpStatus.OK);//http ответ с пустым телом и со статусом ОК
    }




    /*
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
    } */
    @ExceptionHandler //ловит исключение MeasurementException
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(//если исключение выброшено, создаём ErrorResponse
                e.getMessage(),//передаётся сообщение, которое сконструировали в ErrorsUtil
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//отправляется обратно клиенту со статусом BAD_REQUEST (400)
    }




    /*
    private Person convertToPerson(PersonDTO personDTO) {
        //ModelMapper modelMapper = new ModelMapper();//создаём объект вручную. В будущем делегируем это спрингу
        return modelMapper.map(personDTO, Person.class);//маппинг между дто и моделями

        //Person person = new Person();
        //person.setName(personDTO.getName());
        //person.setAge(personDTO.getAge());
        //person.setEmail(personDTO.getEmail());
        //return person; //возвращается объект не доделанный, с нулями
    }*/
    private Sensor convertToSensor(SensorDTO sensorDTO) {
        //return modelMapper.map(personDTO, Person.class);//маппинг между дто и моделями
        return modelMapper.map(sensorDTO, Sensor.class);  //маппинг между дто и моделями
    }

    /*
    private PersonDTO convertToPersonDTO(Person person){//зеркальный метод для отдачи клиенту полей модели (не всех)
        return modelMapper.map(person, PersonDTO.class);    }*/
    private SensorDTO convertToSensorDTO(Sensor sensor){//зеркальный метод для отдачи клиенту полей модели (не всех)
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
