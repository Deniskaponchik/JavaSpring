package ru.alishev.springcourse.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
//        return jdbcTemplate.query("SELECT * FROM Person", new PersonMaper()); //свой PersonMaper()
        //Прдустановленный BeanPropertyRowMapper<>
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class)); //
    }

    public Person show(int id) {
        // Если указываем знак ? то jdbcTemplate понимает/ждёт PreparedStatement что мы в ? укажем значения
        //return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new PersonMaper())
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                        .stream().findAny().orElse(null);
        //если в строке будет найден объект, то будет возвращён он. в отличном случае null (лучше Error в реальном приложении)
    }

    public void save(Person person) {
        //jdbcTemplate понимает, что PreparedStatement и дальше ждёт значение аргументов
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        //синтаксис с vararg
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}