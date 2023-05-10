package ru.alishev.springcourse.FirstSecurityApp.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;
import ru.alishev.springcourse.FirstSecurityApp.services.PersonDetailsService;


@Component
public class PersonValidator implements Validator { //import org.springframework.validation.Validator;

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {        this.personDetailsService = personDetailsService;    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//можем использовать PersonDetailsService. Лучше реализовать отдельный сервис PeopleService, где возвращать Optional<Person>. Но мы здесь так не делаем
        Person person = (Person) target;
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored){
            return; //всё ОК, пользователь в БД не найден. Можно добавлять нового
        }
        errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}
