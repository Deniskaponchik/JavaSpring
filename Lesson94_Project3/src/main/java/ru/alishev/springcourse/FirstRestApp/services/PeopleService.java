package ru.alishev.springcourse.FirstRestApp.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstRestApp.models.Person;
import ru.alishev.springcourse.FirstRestApp.repositories.PeopleRepository;
import ru.alishev.springcourse.FirstRestApp.util.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)  //import org.springframework.transaction.annotation.Transactional;
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll(); // HB берёт строки из тбл и конвертирует в объекты Java
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        //return foundPerson.orElse(null);
        return foundPerson.orElseThrow(PersonNotFoundException::new);//вернут либо человека, либо наше исключение
    }

    @Transactional  //открываем транзакцию  //import org.springframework.transaction.annotation.Transactional;
    public void save(Person person){//сохраняем полученного человека в БД
        enrichPerson(person);//обогащаяем наш объект и добавляем поля, которые добавляются на самом сервере
        peopleRepository.save(person); //с помощью репозитория
    }

    private void enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("ADMIN");//в целях обучения не будем брать откуда-то из БД имя добавляющего. Просто текстом
    }

}
