package ru.alishev.springcourse.FirstRestApp.controllers;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.FirstRestApp.dto.MeasurementDTO;
import ru.alishev.springcourse.FirstRestApp.dto.MeasurementsResponse;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.services.MeasurementsService;
import ru.alishev.springcourse.FirstRestApp.util.*;
import java.util.List;
import java.util.stream.Collectors;
import static ru.alishev.springcourse.FirstRestApp.util.ErrorsUtil.returnErrorsToClient;


// @Controller
@RestController  //@ResponseBody над каждым методом
//@RequestMapping("/people")
@RequestMapping("/measurements")
public class MeasurementsController {
    //private final PeopleService peopleService;
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;//Необходим при работе с DTO. Уменьшает кол-во кода
    @Autowired
    public MeasurementsController(//PeopleService peopleService
                                  MeasurementsService measurementsService,
                                  MeasurementValidator measurementValidator,
                                  ModelMapper modelMapper ){
        //this.peopleService = peopleService;
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
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
    @GetMapping()
    public MeasurementsResponse getMeasurements() {//хороший тон возвращать не Лист, а обёртку
        // Обычно список из элементов оборачивается в один объект для пересылки
        return new MeasurementsResponse(measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));//обычно не возвращаем просто Лист, а оборачиваем. В данном случае в MeasurementsResponse
    }


    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementsService.findAll().stream().filter(Measurement::isRaining).count();
    }





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
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,//приходит DTO, проверяем, что валидный
                                          BindingResult bindingResult){
        Measurement measurementToAdd = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurementToAdd, bindingResult);//проверяем, что такой сенсор ЕСТЬ в БД
        if (bindingResult.hasErrors())//если есть ошибки при валидации
            returnErrorsToClient(bindingResult);

        measurementsService.addMeasurement(measurementToAdd);//ищем объект Sensor по имени + добавляем время
        return ResponseEntity.ok(HttpStatus.OK);
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
    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        //return modelMapper.map(personDTO, Person.class);//маппинг между дто и моделями
        return modelMapper.map(measurementDTO, Measurement.class);  //маппинг между дто и моделями
    }

    /*
    private PersonDTO convertToPersonDTO(Person person){//зеркальный метод для отдачи клиенту полей модели (не всех)
        return modelMapper.map(person, PersonDTO.class);    }*/
    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){//зеркальный метод для отдачи клиенту полей модели (не всех)
        return modelMapper.map(measurement, MeasurementDTO.class);
    }



}
