package org.example.model;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;

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

    @OneToOne(mappedBy = "person") //поле в классе Passport. Там, где используется mappedBy - это НЕ Owning side
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE) //При сохранении человека связанный с ним паспорт также сохраняется в БД
    private Passport passport;

    public Person(){} //ОБЯЗАТЕЛЬНО

    public Person(String name, int age) {
        //this.id = id;
        this.name = name;
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


    public Passport getPassport() {        return passport;    }

    public void setPassport(Passport passport) {
        this.passport = passport;
        passport.setPerson(this);//двусторонняя связь
    }

    @Override
    public String toString() {
        return "Person{" +  "id=" + id +  ", name='" + name + '\'' +     '}';
    }
}
