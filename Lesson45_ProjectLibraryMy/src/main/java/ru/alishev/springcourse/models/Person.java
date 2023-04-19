package ru.alishev.springcourse.models;
//Алишев. Требуется ИМЕННО 6 версия hibernate.validator
import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 chars")
    private String name;

    @Min(value = 0, message = "year should be greater than zero")
    private int year;

    @NotEmpty(message = "Name should not be empty")
    @Email(message = "email should be valid")
    private String email;

//    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be format: Country, City, Postal Code (6 digits)")
//    private String address;  // Russia, Moscow, 123456

    public Person() {    }
    public Person(int id, String name, int year, String email) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.email = email;
        //this.address = address;
    }

//    public String getAddress() {        return address;    }
//    public void setAddress(String address) {        this.address = address;    }

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
        return year;
    }

    public void setAge(int year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}