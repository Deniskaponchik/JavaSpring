package ru.alishev.springcourse.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.*;
import ru.alishev.springcourse.models.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        // return jdbcTemplate.query("SELECT * FROM Person", new PersonMaper()); //свой PersonMaper()
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));   // Предустановленный BeanPropertyRowMapper<>
                //Делает из полученного запроса лист бинов класса Person
    }

    public Person show(int id) {
        // Если указываем знак ? то jdbcTemplate понимает/ждёт PreparedStatement что мы в ? укажем значения
        //return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new PersonMaper())
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        //если в строке будет найден объект, то будет возвращён он. в отличном случае null (лучше Error в реальном приложении)
    }

    public void save(Person person) {
        //jdbcTemplate понимает, что PreparedStatement и дальше ждёт значение аргументов
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES(?, ?)",
                //person.getName(), person.getAge(), person.getEmail(), person.getAddress());
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        //синтаксис с vararg
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE id=?",
                //updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public Optional<Person> getPersonByFullName(String fullName){ //нужен для уникальности валидации ФИО
        //Мы предполагаем,что можем вернуться объект person,а может и не вернуться. избавляет от проверки на null итоговый объект.
        //передаём fullName в качестве аргумента в PreparedStatement
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name = ?",
                new Object[]{fullName}, // ???
                //мапим resultset, который пришёл от базы данных, в человека
                //new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null); //без Optional
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public List<Book> getBooksByPersonId(int id) { //id ЧЕЛОВЕКА. список книг, который взял человек.
        //Здесь JOIN не нужен. Получили с помощью метода show в контроллере
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }



}