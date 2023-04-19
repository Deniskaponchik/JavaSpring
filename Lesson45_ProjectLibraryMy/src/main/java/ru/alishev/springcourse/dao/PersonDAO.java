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
    public List<Person> index() {
        // return jdbcTemplate.query("SELECT * FROM Person", new PersonMaper()); //свой PersonMaper()
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));   // Предустановленный BeanPropertyRowMapper<>
                //Делает из полученного запроса лист бинов класса Person
    }
    public List<Book> showPersonBooks(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE user_id = ?",
                new BeanPropertyRowMapper<>(Book.class)); //получаем ЛИСТ БИНОВ класса Book
                // Возвращаем в @GetMapping("/{id}")
    }

    public Person show(int id) {
        // Если указываем знак ? то jdbcTemplate понимает/ждёт PreparedStatement что мы в ? укажем значения
        //return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new PersonMaper())
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        //если в строке будет найден объект, то будет возвращён он. в отличном случае null (лучше Error в реальном приложении)
    }
    public Optional<Person> show(String email){
        //Optional -класс обёртка. В данном случае избавляет от проверки на null итоговый объект.
        //Мы предполагаем, что можем вернуться объект person, а может и не вернуться
        //передаём email в качестве аргумента в PreparedStatement
        return jdbcTemplate.query("SELECT * FROM Person WHERE email = ?",
                new Object[]{email},
                //мапим resultset, который пришёл от базы данных, в человека
                //new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null); //без Optional
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }


    public void save(Person person) {
        //jdbcTemplate понимает, что PreparedStatement и дальше ждёт значение аргументов
        jdbcTemplate.update("INSERT INTO Person(name, age, email, address) VALUES(?, ?, ?, ?)",
                //person.getName(), person.getAge(), person.getEmail(), person.getAddress());
                person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        //синтаксис с vararg
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=?, address=? WHERE id=?",
                //updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }



    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}