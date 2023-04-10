package ru.alishev.springcourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class MusicPlayer {
    private Music music1;
    private Music music2;

    @Autowired
    public MusicPlayer(
            @Qualifier("rockMusic") Music music1,
            @Qualifier("classicalMusic") Music music2
    ){
        this.music1 = music1;
        this.music2 = music2;
    }

    public String playMusic(MusicEnum musicEnum){
        int min = 1;
        int max = 3;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        if(musicEnum.equals(MusicEnum.CLASSICAL)){
            return "Playing: " + music1.getSong().get(randomNum); // + ", " + music2.getSong();
        }
        else{
            return "Playing: " + music2.getSong().get(randomNum);
        }
        //return "Playing: " + music1.getSong() + ", " + music2.getSong();
    }
}
