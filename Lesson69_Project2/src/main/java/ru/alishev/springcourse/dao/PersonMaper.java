package ru.alishev.springcourse.dao;
import org.springframework.jdbc.core.RowMapper;
import ru.alishev.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMaper implements RowMapper<Person> { //import org.springframework.jdbc.core.RowMapper;

    @Override //делает из полученной строки из БД объекты класса Person
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        //person.setName(resultSet.getString("name"));
        //person.setAge(resultSet.getInt("age"));
        //person.setEmail(resultSet.getString("email"));
        return person;
    }
}
