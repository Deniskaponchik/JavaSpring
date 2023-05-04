package ru.alishev.springcourse.Project2Boot.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.Project2Boot.models.Book;

import java.util.List;


@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> { //Integer - Тип первичного ключа у класса
    //названия методов берутся не из головы, а ДОЛЖНЫ соответствовать шаблонам:
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation


    //Здесь УЖЕ будут методы: finById, findAll, delete, save и т.д.       update НЕ будет


    /*Кастомные методы
    List<Book> findByName(String name);  //Найдём людей по имени

    List<Book> findByNameOrderByAge (String name);  // Найдём людей по имени и отсортируем по возрасту

    List<Book> findByEmail (String email);

    List<Book> findByNameOrEmail (String name, String email); //передать в метод нужно И имя И email, но найдёт ИЛИ
    */


    //List<Book> findByNameStartingWith (String startingWith); //Поиск людей, у которых имена начинаются со строки startingWith
    List<Book> findByTitleStartingWith (String title); //Поиск людей, у которых имена начинаются со строки startingWith
    //Optional<Book> findDistinctByTitleStartingWith (String startingWith); //Поиск книг, у которых title начинаются со строки startingWith



}
