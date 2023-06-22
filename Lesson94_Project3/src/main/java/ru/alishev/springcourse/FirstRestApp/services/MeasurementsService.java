package ru.alishev.springcourse.FirstRestApp.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;
import ru.alishev.springcourse.FirstRestApp.repositories.MeasurementRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)  //import org.springframework.transaction.annotation.Transactional;
public class MeasurementsService {
    //private final PeopleRepository peopleRepository;
    private final MeasurementRepository measurementRepository;
    private final SensorsService sensorsService;

    @Autowired
    //public MeasurementsService(PeopleRepository peopleRepository) {        this.peopleRepository = peopleRepository;    }
    public MeasurementsService(MeasurementRepository measurementRepository,
                               SensorsService sensorsService){
        this.measurementRepository = measurementRepository;
        this.sensorsService = sensorsService;
    }


    /*
    public List<Person> findAll() {
        return peopleRepository.findAll(); // HB берёт строки из тбл и конвертирует в объекты Java
    }*/
    public List<Measurement> findAll() {
        return measurementRepository.findAll(); // HB берёт строки из тбл и конвертирует в объекты Java
    }
    /*
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        //return foundPerson.orElse(null);
        return foundPerson.orElseThrow(PersonNotFoundException::new);//вернут либо человека, либо наше исключение
    }*/



    /*
    @Transactional  //открываем транзакцию //import org.springframework.transaction.annotation.Transactional;
    public void save(Person person){       //сохраняем полученного человека в БД
        enrichPerson(person);              //обогащаяем наш объект и добавляем поля, которые добавляются на самом сервере
        peopleRepository.save(person);     //с помощью репозитория
    }*/
    @Transactional  //открываем транзакцию //import org.springframework.transaction.annotation.Transactional;
    public void addMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    /*
    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");//в целях обучения не будем брать откуда-то из БД имя добавляющего. Просто текстом
    }*/
    public void enrichMeasurement(Measurement measurement) {
        // мы должны сами найти сенсор из БД по имени и вставить объект из Hibernate persistence context'а
        //потому что в JSON приходит просто строка
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
        measurement.setMeasurementDateTime(LocalDateTime.now());
    }

}
