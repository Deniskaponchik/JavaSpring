package ru.alishev.springcourse;

//@Component
//@Scope("prototype")
public class ClassicalMusic implements Music{
    public void doMyInit(){
        System.out.println("Doing my initialization");
    }

    public void doMyDestroy() {
        System.out.println("Doing my destroy");
    }
    @Override
    public String getSong() {
        return "Hungarian Rhapsody";
    }
}
