package ru.alishev.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.models.Person;
import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> { //Integer - Тип первичного ключа у класса
    //Здесь уже будут методы: finById, findAll, delete, save и т.д.  update не будет

    //названия методов берутся не из головы, а ДОЛЖНЫ соответствовать шаблонам:
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation


    //Кастомные методы
    //Найдём людей по имени
    List<Person> findByName(String name);


    // Найдём людей по имени и отсортируем по возрасту
    List<Person> findByNameOrderByAge (String name);

    //
    List<Person> findByEmail (String email);

    //Поиск людей, у которых имена начинаются со строки startingWith
    List<Person> findByNameStartingWith (String startingWith);


    List<Person> findByNameOrEmail (String name, String email); //передать в метод нужно И имя И email, но найдёт ИЛИ


}
