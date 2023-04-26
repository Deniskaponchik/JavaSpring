package ru.alishev.springcourse.models;
//Алишев. Требуется ИМЕННО 6 версия hibernate.validator
import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "FullName should be between 2 and 100 chars")
    private String fullName;
    //private String name;

    @Min(value = 1900, message = "yearOfBirth should be greater than 1900")
    private int yearOfBirth;
    //private int year;

    /*
    @NotEmpty(message = "Name should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be format: Country, City, Postal Code (6 digits)")
    private String address;  // Russia, Moscow, 123456
    */

    public Person() {    } //Для спринга ОБЯЗАТЕЛЬНО нужен пустой конструктор
    public Person(int id, String fullName, int yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

//    public String getAddress() {        return address;    }
//    public void setAddress(String address) {        this.address = address;    }

    public int getId() {        return id;    }
    public void setId(int id) {        this.id = id;    }
    public String getFullName() {        return fullName;    }
    public void setFullName(String fullName) {        this.fullName = fullName;    }
    public int getYearOfBirth() {        return yearOfBirth;    }
    public void setYearOfBirth(int yearOfBirth) {        this.yearOfBirth = yearOfBirth;    }
}