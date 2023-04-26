package ru.alishev.springcourse.models;
//Алишев. Требуется ИМЕННО 6 версия hibernate.validator
import javax.validation.constraints.*;

public class Book {
    private int id;
    //private int userID; //у Алишева нет
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 chars")
    private String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 chars")
    private String author;

    @Min(value = 0, message = "Year should be greater than zero")
    private int year;

    /*
    @NotEmpty(message = "Name should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be format: Country, City, Postal Code (6 digits)")
    private String address;  // Russia, Moscow, 123456
     */

    public Book() {    } //Для спринга нужен пустой конструктор
    public Book(int id, int userId, int userID, String  title, int year, String author) {
        this.id = id;
        //this.userID = userID;
        this.title =  title;
        this.year = year;
        this.author = author;
        //this.address = address;
    }

    public int getId() {        return id;    }
    public void setId(int id) {        this.id = id;    }
    //public int getUserID() {        return userID;    }
    //public void setUserID(int userID) {        this.userID = userID;    }
    public String getTitle() {        return title;    }
    public void setTitle(String title) {        this.title = title;    }
    public String getAuthor() {        return author;    }
    public void setAuthor(String author) {        this.author = author;    }
    public int getYear() {        return year;    }
    public void setYear(int year) {        this.year = year;    }

}