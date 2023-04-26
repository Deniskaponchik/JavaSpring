package org.example.model;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="passport")
public class Passport { //имплементируем Serializable, если не стандартный первичный ключ не числовой

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="passport_number")
    private int passportNumber;

    @OneToOne
    @JoinColumn(name="person_id", referencedColumnName = "id") //Owning Side
    private Person person;

    public Passport(){}

    public Passport(int passportNumber){
        this.passportNumber = passportNumber;
    }
    /*
    public Passport(Person person, int passportNumber) {
        this.person = person;
        this.passportNumber = passportNumber;
    }*/

    public int getId() {        return id;    }

    public void setId(int id) {        this.id = id;    }

    public int getPassportNumber() {        return passportNumber;    }

    public void setPassportNumber(int passportNumber) {        this.passportNumber = passportNumber;    }
    public Person getPerson() {        return person;    }

    public void setPerson(Person person) {        this.person = person;    }



    @Override
    public String toString() {
        return "Passport{" +
                "person=" + person +
                ", passportNumber=" + passportNumber +
                '}';
    }
}
