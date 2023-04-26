package org.example;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main( String[] args ){
        //import org.hibernate.cfg.Configuration;
        //Конфигурация АВТОМАТИЧЕСКИ подцепила файл конфигурации hibernate.properties из директории resources
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            //Person person = session.get(Person.class, 1); //ищет сущность Person в таблице person с id = 1
            Person person1 =new Person("Test1", 30);
            Person person2 =new Person("Test2", 40);
            Person person3 =new Person("Test3", 50);

            session.save(person1);
            session.save(person2);
            session.save(person3);

            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
