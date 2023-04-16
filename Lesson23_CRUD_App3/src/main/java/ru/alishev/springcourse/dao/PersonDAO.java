package ru.alishev.springcourse.dao;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {   //блок инициализации
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
        people.add(new Person(++PEOPLE_COUNT, "Katy"));
    }

    public List<Person> index() {
        return people;
    }
    public Person show(int id){
        //отфильтрую (человека -> его id, чтобы равнялся id, пришедшего в качестве аргумента). найду этого человека,
        // если он есть, если нет, верну null
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatedPerson){
        //id - обновляемого, updatedPerson - пришедший с модели новый
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
    }
    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
