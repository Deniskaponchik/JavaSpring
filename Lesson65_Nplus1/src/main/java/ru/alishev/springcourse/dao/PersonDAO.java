package ru.alishev.springcourse.dao;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Person;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonDAO {

    //используем в случае JPA
    private final EntityManager entityManager;  //import javax.persistence.EntityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)   //import org.springframework.transaction.annotation.Transactional;
    public void testNPlus1(){         //этот метод вызовем в контроллере
        //Session session = sessionFactory.getCurrentSession(); //раньше вызывали
        Session session = entityManager.unwrap(Session.class);  //start Hibernate session

        //List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();  //N+1 PROBLEM
        Set<Person> people = new HashSet<Person>(session.createQuery("select p from Person p LEFT JOIN FETCH p.items")
                .getResultList()); //FETCH - данные хотим получить - загрузить данные в оперативную память. Избавляет от проблемы N+1
        //Set позволяет избавить от дубликатов. Для его работы потребуется реализовать методы Hashcode и equals в классе Person
        for (Person person : people)
            System.out.println("Person " + person.getName() + " has: " + person.getItems());
        //HB не будет вызывать запрос каждый раз (person.get), потому что будет понимать, что они уже сделаны

    }





    /* Отказались на уроке ??  Какое-то время не нужно было в Spring Data JPA
    private final SessionFactory sessionFactory;
    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)   //import org.springframework.transaction.annotation.Transactional;
    public List<Person> index() {     //в методе index изменений не вносим, поэтому readOnly = true
        Session session = sessionFactory.getCurrentSession();
        //транзакция откроется автоматически
        return session.createQuery("select p from Person p", Person.class).getResultList();
    }
    @Transactional(readOnly = true)  //Аннотация необходима для ЛЮБОЙ работы с HB
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional(readOnly = false)  //Аннотация необходима для ЛЮБОЙ работы с HB
    public void save(Person person) { //Добавление нового человека в БД
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional(readOnly = false)  //Аннотация необходима для ЛЮБОЙ работы с HB
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id); //Persistence context
        personToBeUpdated.setName(updatedPerson.getName());       // сразу же вносят обновления в БД
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
        //session.update(personToBeUpdated); //НЕ НУЖНО. все сеттеры сразу будут вносить инфо у человека в БД
    }

    @Transactional(readOnly = false)  //Аннотация необходима для ЛЮБОЙ работы с HB
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));  //delete = remove
        //session.delete(session.get(Person.class, id)); //delete = remove
    }
    */




    /* JdbcTemplate
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index() {
        //return jdbcTemplate.query("SELECT * FROM Person", new PersonMaper()); //свой PersonMaper()
        //Предустановленный BeanPropertyRowMapper<>
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class)); //
    }
    public Person show(int id) {
        // Если указываем знак ? то jdbcTemplate понимает/ждёт PreparedStatement что мы в ? укажем значения
        //return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new PersonMaper())
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                        .stream().findAny().orElse(null);
        //если в строке будет найден объект, то будет возвращён он. в отличном случае null (лучше Error в реальном приложении)
    }

    public void save(Person person) {
        //jdbcTemplate понимает, что PreparedStatement и дальше ждёт значение аргументов
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        //синтаксис с vararg
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
    */




}