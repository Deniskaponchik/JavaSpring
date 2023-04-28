package ru.alishev.springcourse.models;

//Алишев. Требуется ИМЕННО 6 версия hibernate.validator
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
}



























