package ru.alishev.springcourse.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }
    @Override
    public void validate(
            Object o,          //сюда будет передаваться объект класса Person
            Errors errors) {   //сюда будет передаваться bindingResult
        Person person = (Person) o;

        //1. Проверяем, что email в базе нет
        //if(personDAO.show(person.getEmail()) != null){
        if(personDAO.show(person.getEmail()).isPresent()){  //лучше, чем проверка на null. Доступен у объектов Optional
            //1.какое поле вызвало ошибку, 2.код ошибки(нас не интересует), 3. code of error
            errors.rejectValue("email", "", "This email is already taken");
            //в rejectValue можно помещать и другие поля, например, name
        }

        //2. Проверяем, что у человека имя начинается с заглавной буквы
        if(!Character.isUpperCase(person.getName().codePointAt(0)))
            errors.rejectValue("name", "", "Name should start with a capital letter");

    }
}
