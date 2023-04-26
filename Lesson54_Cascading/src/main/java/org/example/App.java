package org.example;
import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class App {
    public static void main( String[] args ){
        //import org.hibernate.cfg.Configuration;
        //Конфигурация АВТОМАТИЧЕСКИ подцепила файл конфигурации hibernate.properties из директории resources
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = new Person("test cascading", 30);
            person.addItem(new Item("Item1"));
            person.addItem(new Item("Item2"));
            person.addItem(new Item("Item3"));

            session.save(person);  //без каскадирования сохранится ТОЛЬКО человек БЕЗ связанного с ним товара
            //session.persist(person); //c каскадированием(person+item)


            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
