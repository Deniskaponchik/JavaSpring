package ru.alishev.springcourse.Project2Boot.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.Project2Boot.models.Person;
import java.util.Optional;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> { //Integer - Тип первичного ключа у класса
    //названия методов берутся не из головы, а ДОЛЖНЫ соответствовать шаблонам:
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation


    //Здесь уже будут методы: finById, findAll, delete, save и т.д.  update не будет



    /*Кастомные методы
    List<Person> findByName(String name);  //Найдём людей по имени

    List<Person> findByNameOrderByAge (String name);  // Найдём людей по имени и отсортируем по возрасту

    List<Person> findByEmail (String email);

    List<Person> findByNameStartingWith (String startingWith);  //Поиск людей, у которых имена начинаются со строки startingWith

    List<Person> findByNameOrEmail (String name, String email); //передать в метод нужно И имя И email, но найдёт ИЛИ
    */

    Optional<Person> findByFullName(String fullName); //Нужен для PersonValidator при попытке добавления нового человека в БД.



}
