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
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Passport.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            /* Создание человека и его паспорта
            Person person = new Person("test person", 50);
            Passport passport = new Passport(12345);
            person.setPassport(passport);  //хорошая практика устанавливать с двух сторон. Чтобы в кешэ HB были актуальные данные
            session.save(person);  //Сохранение будет каскадированным, потому что мы указали @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
            //session.persist(person); //c каскадированием(person+item)
            */
            /*прочитаем человека и получим паспорт
            Person person = session.get(Person.class, 9);
            System.out.println(person.getPassport().getPassportNumber());
            */
            /*прочитаем паспорт и получим человека
            Passport passport = session.get(Passport.class, 9);
            System.out.println(passport.getPerson().getName());
            */
            /*Изменим номер паспорта у человека
            Person person = session.get(Person.class, 9);
            person.getPassport().setPassportNumber(777777); //СРАЗУ сделается запрос в БД. Состояние PERSISTENCE (MANAGE)
            */
            //Удалим человека и посмотрим, как каскадно будет удаляться его паспорт
            Person person = session.get(Person.class, 9);
            session.remove(person);//каскадирование на уровне HB мы не настраивали

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
