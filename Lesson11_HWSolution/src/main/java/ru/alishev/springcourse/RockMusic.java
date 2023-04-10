package ru.alishev.springcourse;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class RockMusic implements Music{
    @Override
    //public String getSong() {        return "Wind cries Mary";    }
    public List<String> getSong(){
        List<String> rmArray = new ArrayList<>();
        rmArray.add("cm1");
        rmArray.add("cm2");
        rmArray.add("cm3");
        return rmArray;
    }
}
