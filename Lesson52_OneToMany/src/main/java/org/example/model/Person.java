package org.example.model;
import javax.persistence.*;
import java.util.List;


@Entity  //СУЩНОСТИ. связывает объекты классов с сущностями в БД. Классы с этой аннотацией ДОЛЖНЫ содержать пустой конструктор + поле id
@Table(name = "Person") //имя таблицы в БД. Можно не указывать, если название класса = название таблицы
public class Person {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //указываем Hibernate не трогать эту колонку. Всё на стороне postgres
    //@GeneratedValue(strategy = GenerationType.SEQUANCE,
    // generator="seq_generator_person")   //
    //SequenceGenerator(name="seq_generator_person",
    // sequenceName="person_id_seq",
    // allocationSize = 1)    // умножаем на это число. указывать такой же, как в БД
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private int age;

    @OneToMany(mappedBy = "owner") //JoinColumn писать здесь не нужно
    private List<Item> items;

    public Person(){} //ОБЯЗАТЕЛЬНО

    public Person(String name, int age) {
        //this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Person{" +  "id=" + id +  ", name='" + name + '\'' +   ", age=" + age +     '}';
    }
}
