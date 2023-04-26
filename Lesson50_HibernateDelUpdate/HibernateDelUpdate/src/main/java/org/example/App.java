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

            Person person = new Person("some name", 60);
            session.save(person);

            //Person person = session.get(Person.class, 2); //получаем person с id=2
            // person.setName("New name"); //UPDATE. после commit hibernate обновит данные в БД
            // session.delete(person);

            session.getTransaction().commit();

            System.out.println(person.getId()); //получить id Добавленного в БД person

        } finally {
            sessionFactory.close();
        }
    }
}
