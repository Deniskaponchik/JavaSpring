package ru.alishev.springcourse.FirstRestApp.util;

public class PersonErrorResponse {//объекты этого класса будут отправляться по сети в случае ошибок
    private String message; //сообщение об ошибке
    private long timestamp; //отметка времени в мс

    public PersonErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
