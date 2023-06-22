package ru.alishev.springcourse.FirstRestApp.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import ru.alishev.springcourse.FirstRestApp.repositories.SensorRepository;
import ru.alishev.springcourse.FirstRestApp.util.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)  //import org.springframework.transaction.annotation.Transactional;
public class SensorsService {
    //private final PeopleRepository peopleRepository;
    private final SensorRepository sensorRepository;
    @Autowired
    //public SensorsService(PeopleRepository peopleRepository) {        this.peopleRepository = peopleRepository;    }
    public SensorsService(SensorRepository sensorRepository) {        this.sensorRepository = sensorRepository;    }


    /*
    public List<Person> findAll() {
        return peopleRepository.findAll(); // HB берёт строки из тбл и конвертирует в объекты Java
    }*/


    /*
    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        //return foundPerson.orElse(null);
        return foundPerson.orElseThrow(PersonNotFoundException::new);//вернут либо человека, либо наше исключение
    }*/
    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }


    /*
    @Transactional  //открываем транзакцию //import org.springframework.transaction.annotation.Transactional;
    public void save(Person person){       //сохраняем полученного человека в БД
        enrichPerson(person);              //обогащаяем наш объект и добавляем поля, которые добавляются на самом сервере
        peopleRepository.save(person);     //с помощью репозитория
    }*/
    @Transactional
    public void register(Sensor sensor) {
        sensorRepository.save(sensor);//метод save есть в репозитории по умолчанию
    }

    /* Для сенсоров этот метод не использую
    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");//в целях обучения не будем брать откуда-то из БД имя добавляющего. Просто текстом
    }*/

}
