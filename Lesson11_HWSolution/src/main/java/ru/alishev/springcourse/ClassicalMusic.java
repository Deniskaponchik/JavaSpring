package ru.alishev.springcourse;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassicalMusic implements Music{
    @Override
    //public String getSong() {return "Hungarian Rhapsody";    }
    public List<String> getSong(){
        List<String> cmArray = new ArrayList<>();
        cmArray.add("cm1");
        cmArray.add("cm2");
        cmArray.add("cm3");
        return cmArray;
    }
}
