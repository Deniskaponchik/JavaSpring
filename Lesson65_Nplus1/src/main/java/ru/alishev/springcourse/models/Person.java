package ru.alishev.springcourse.models;
/* hibernate НЕ РАБОТАЕТ
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Size;
import org.hibernate.validator.constraints.Min;
import org.hibernate.validator.constraints.Email;
 */
/* jakarta НЕ РАБОТАЕТ
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Email;
*/
//Алишев. Требуется ИМЕННО 6 версия hibernate.validator
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="Person")  //import javax.persistence.Table;
public class Person {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 chars")
    private String name;


    @Column(name="age")
    @Min(value = 0, message = "Age should be greater than zero")
        private int age;

    @Column(name="email")
    @NotEmpty(message = "Name should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)               //import javax.persistence.*;
    @DateTimeFormat(pattern = "dd/MM/yyyy")    //сможем парсить строку даты из формы. Если дата будет не в валидном формате, то возникнет НЕ user friendly ошибка
    private Date dateOfBirth;                  //import java.util.Date;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; //дата будет ложиться НЕ в контроллере, а в Сервисе (бизнес-логика)

    @Enumerated(EnumType.STRING)
    //@Enumerated(EnumType.ORDINAL)  //ORDINAL каждому из перечислений выдаёт индекс (HAPPY = 0, SAD = 1 и т.д.) И сохран. в тбл
    private Mood mood;


    @OneToMany(mappedBy = "owner")
    private List<Item> items;

    public Person() {    }

    public Person(String name, int age, String email) {
        //this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
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

    public String getEmail() {        return email;    }
    public void setEmail(String email) {        this.email = email;    }

    public List<Item> getItems() {        return items;    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getDateOfBirth() {        return dateOfBirth;    }

    public void setDateOfBirth(Date dateOfBirth) {        this.dateOfBirth = dateOfBirth;    }

    public Date getCreatedAt() {        return createdAt;    }

    public void setCreatedAt(Date createdAt) {        this.createdAt = createdAt;    }

    public Mood getMood() {        return mood;    }
    public void setMood(Mood mood) {        this.mood = mood;    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                '}';
    }
}



























