package ru.alishev.springcourse.dao;
import org.springframework.jdbc.core.RowMapper; //ИМЕННО ТАКОЙ ПАКЕТ !!!
import ru.alishev.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonMaper implements RowMapper<Person> {
    //в jdbcTemplate и в данном проекте НЕ используется
    //https://www.udemy.com/course/spring-alishev/learn/lecture/31009314#content
    @Override //делает из полученной строки из БД объекты класса Person
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setFullName(resultSet.getString("fullName"));
        person.setYearOfBirth(resultSet.getInt("yearOfBirth"));
        //person.setEmail(resultSet.getString("email"));
        return person;
    }
}
