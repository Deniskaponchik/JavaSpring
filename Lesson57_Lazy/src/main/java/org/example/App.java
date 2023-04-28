package org.example;
import org.example.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class App {
    public static void main( String[] args ){        //import org.hibernate.cfg.Configuration;
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);
        //Конфигурация АВТОМАТИЧЕСКИ подцепила файл конфигурации hibernate.properties из директории resources
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            /* 1. Загрузка товаров у человека (ленивая)
            Person person = session.get(Person.class, 1); //Загружается человек без связанных с ним товаров
            System.out.println("Получили человека");
            //Получим связанные сущности (Lazy)  = OneToMany
            System.out.println(person.getItems()); //OneToMany. Создаётся новый SQL-Запрос
            session.getTransaction().commit(); //session.close();
            */
            /* 2. Получим товар. потом у этого товара получим человека
            Item item = session.get(Item.class, 1); // Eager = ManyToOne
            System.out.println("Получили товар");
            System.out.println(item.getOwner()); //SQL не делается. Уже получен владелец в 1 запросе
            session.getTransaction().commit(); //session.close();
            */
            /* 3. Добавили в класс Person @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
            Person person = session.get(Person.class, 1); //Загружается человек УЖЕ со связанными с ним товарами
            System.out.println("Получили человека");
            //Получим связанные сущности уже Eager
            System.out.println(person.getItems()); //SQL-Запрос НЕ создаётся
            session.getTransaction().commit(); //session.close();
            */
            /* 4. Добавили в класс Person @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
            Person person = session.get(Person.class, 1); //
            System.out.println("Получили человека");
            System.out.println(person); //
            //person.getItems(); //при таком запуске получим ошибку, т.к. Java оптимизирует код, а результат вызова никуда не передаётся.
            // Поэтому обворачиваем в sout или Initialize
            Hibernate.initialize(person.getItems());
            session.getTransaction().commit(); //session.close();
            //System.out.println(person.getItems()); //Попытка получения товаров человека вне сессии. Получим ошибку, если LAZY
            //failed to lazily initialize a collection of role: org.example.model.Person.items, could not initialize proxy - no Session
            */
            /* 5. Выход из сессии, создание новой, подгрузка товаров вне сессии
            Person person = session.get(Person.class, 1); //
            System.out.println("Получили человека");
            session.getTransaction().commit(); //session.close();
            System.out.println("Сессия закончилась");
            session = sessionFactory.getCurrentSession(); //открываем сессию и транзакцию снова
            session.beginTransaction();  //можем сделать в любом месте кода
            System.out.println("Внутри второй транзакции");
            person = (Person) session.merge(person);
            Hibernate.initialize(person.getItems());
            session.getTransaction().commit(); //session.close();
            System.out.println("Вне второй сессии");
            System.out.println(person.getItems()); //связанные товары были загружены
            */
            // 6. Альтернатива 5 способу - написать HQL-код. Но 5 способ лучше и короче
            Person person = session.get(Person.class, 1); //
            System.out.println("Получили человека");
            session.getTransaction().commit(); //session.close();
            System.out.println("Сессия закончилась");

            session = sessionFactory.getCurrentSession(); //открываем сессию и транзакцию снова
            session.beginTransaction();  //можем сделать в любом месте кода
            System.out.println("Внутри второй транзакции");
            List<Item> items = session.createQuery("select i from Item i where i.owner.id = :personId", Item.class)
                    .setParameter("personId", person.getId()).getResultList();
            System.out.println(items);
            session.getTransaction().commit(); //session.close();
            System.out.println("Вне второй сессии");




        } finally {
            sessionFactory.close();
        }
    }
}
































