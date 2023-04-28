package ru.alishev.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.repositories.PeopleRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)  //import org.springframework.transaction.annotation.Transactional;
//если помечаем @Transactional целый класс, то все ПУБЛИЧНЫЕ методы этого класса будут @Transactional
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){  // @Transactional(readOnly = true) по умолчанию для всего класса
        return peopleRepository.findAll(); //ни одного метода в PeopleRepository не реализовали, но у нас есть по умолчанию метод findAll()
    }


    public Person findOne(int id){ //было полезно, что указали, что у нас id это целое число (в interface PeopleRepository)
        Optional<Person> foundPerson = peopleRepository.findById(id); //можем найти человека, а можем и не найти
        return foundPerson.orElse(null);
    }

    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void save(Person person){
        peopleRepository.save(person); //ничего не нужно реализовывать - за нас уже всё реализовано
    }


    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson); //в репозитории есть соглашение, что для ДОБАВЛЕНИЯ сущности и ОБНОВЛЕНИЯ сущности используется один и тот же метод
        //метод save увидит, что передаётся человек с id, который уже есть в тбл. и обновит значения у существуещего человека
    }


    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
